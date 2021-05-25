package com.criteriontech.digidoctormax.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.adapters.ChatAdapter;
import com.criteriontech.digidoctormax.adapters.SmartSuggestionAdapter;
import com.criteriontech.digidoctormax.databinding.FragmentChatBinding;
import com.criteriontech.digidoctormax.drViewModel.DrViewModel;
import com.criteriontech.digidoctormax.interfaces.ChatInterface;
import com.criteriontech.digidoctormax.interfaces.NewApiInterface;
import com.criteriontech.digidoctormax.interfaces.SuggestionInterface;
import com.criteriontech.digidoctormax.model.ChatModel;
import com.criteriontech.digidoctormax.model.NoOfPatientValue;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.mlkit.nl.smartreply.SmartReply;
import com.google.mlkit.nl.smartreply.SmartReplyGenerator;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import com.google.mlkit.nl.smartreply.TextMessage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.criteriontech.digidoctormax.utils.AppUtils.KEY_APPOINTMENT_ID;


public class ChatFragment extends Fragment implements ChatInterface, SuggestionInterface {
    private static final String TAG = "ChatFragment";
    FragmentChatBinding fragmentChatBinding;
    NavController navController;
    ChatAdapter adapter;
    List<ChatModel> chats;
    String AppointmentId = null;
    String doId = null;

    String uid;

    DrViewModel viewModel;
    NoOfPatientValue NoOfPatientValue;


    //For Smart Reply
    SmartSuggestionAdapter suggestionAdapter;
    List<String> suggestionMsg;
    ArrayList<TextMessage> conversation = new ArrayList<TextMessage>();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentChatBinding = FragmentChatBinding.inflate(getLayoutInflater());
        return fragmentChatBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (null == getArguments())
            return;

        viewModel = new ViewModelProvider(requireActivity()).get(DrViewModel.class);


        uid = String.valueOf(getArguments().getInt("memberId"));

        AppointmentId = getArguments().getString(KEY_APPOINTMENT_ID);
        doId = String.valueOf(SharedPrefManager.getInstance(requireActivity()).getUser().getUserLoginId());

        //Showing the title
        Objects.requireNonNull(Navigation.findNavController(view).getCurrentDestination()).setLabel(getArguments().getString("docName"));

        chats = new ArrayList<>();
        adapter = new ChatAdapter(this, chats, uid);
        fragmentChatBinding.chatRec.setAdapter(adapter);

        suggestionMsg = new ArrayList<>();
        suggestionAdapter = new SmartSuggestionAdapter(suggestionMsg, this::onSuggestionItemClicked);
        fragmentChatBinding.recSmartSuggestion.setAdapter(suggestionAdapter);


        loadData();


        fragmentChatBinding.btnSendMsg.setOnClickListener(view1 -> {
            if (fragmentChatBinding.editTextTextPersonName4.getText().toString().isEmpty())
                return;
            sendMsg(fragmentChatBinding.editTextTextPersonName4.getText().toString());
        });

    }

    private void loadData() {
        NoOfPatientValue appointmentModel = new NoOfPatientValue();
        appointmentModel.setAppointmentId(Integer.valueOf(AppointmentId));

        viewModel.getChatData(appointmentModel).observe(getViewLifecycleOwner(), (List<ChatModel> chatModelList) -> {
            if (null != chatModelList) {
                chats.clear();
                chats.addAll(chatModelList);
            }
            if (chats.size() > 1) {
                addSenderMsg(chats.get(0).getMessage());
                addReceiverMsg(chats.get(1).getMessage());
                getSmartReply();
            }

            if (chats.size() > 0)
                fragmentChatBinding.chatRec.scrollToPosition(0);
            adapter.notifyDataSetChanged();
            updateVisibility();
        });

    }

    private void updateVisibility() {
        fragmentChatBinding.noChatsLay.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        fragmentChatBinding.chatRec.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }


    public ChatModel getChat(String msg) {
        ChatModel chatModel = new ChatModel();
        chatModel.setAppointmentId(AppointmentId);
        chatModel.setMessage(msg);
        chatModel.setSenderId(doId);
        chatModel.setReceiverId(uid);
        chatModel.setSeen(false);
        chatModel.setServiceProviderTypeId("3");//6 for patient
        chatModel.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return chatModel;
    }

    private void sendMsg(String msg) {
        adapter.notifyDataSetChanged();
        adapter.addChatItems(getChat(msg));
        ApiUtils.getChatResponse(ApiUtils.sendMsg(getChat(msg)), new NewApiInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<ChatModel> chatModels = (List<ChatModel>) obj;
                Log.d(TAG, "onSuccess: msg send " + chatModels.toString());
                chats.clear();
                chats.addAll(chatModels);

                updateVisibility();


                if (chats.size() > 0)
                    fragmentChatBinding.chatRec.scrollToPosition(0);
               // getSmartReply();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String msg) {
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
        fragmentChatBinding.editTextTextPersonName4.setText("");
        updateVisibility();
    }


    public void onChatItemClicked(Object obj) {

    }

    @Override
    public void onSuggestionItemClicked(String msg) {
        sendMsg(msg);
    }


    private void addSenderMsg(String msg) {
        conversation.add(TextMessage.createForLocalUser(
                msg, System.currentTimeMillis()));
    }

    private void addReceiverMsg(String msg) {
        conversation.add(TextMessage.createForRemoteUser(
                msg, System.currentTimeMillis(), doId));
    }

    private void getSmartReply() {
        SmartReplyGenerator smartReply = SmartReply.getClient();
        smartReply.suggestReplies(conversation)
                .addOnSuccessListener(result -> {
                    if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                        Log.d(TAG, "onSuccess: language not supported !!");
                    } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                        suggestionMsg.clear();
                        for (SmartReplySuggestion suggestionMsg1 : result.getSuggestions()) {
                            Log.d(TAG, "smartReply: " + suggestionMsg1.getText());
                            suggestionMsg.add(suggestionMsg1.getText());
                        }
                        suggestionAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                });

    }
}