package com.criteriontech.digidoctormax.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.adapters.BankDetailsAdapter;
import com.criteriontech.digidoctormax.databinding.BankDetailsListFragmentBinding;
import com.criteriontech.digidoctormax.model.GetBankDetailsModel;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BankDetailsListFragment extends Fragment {
    NavController navController;
    BankDetailsListFragmentBinding binding;

    BankDetailsAdapter bankDetailsAdapter;

    List<GetBankDetailsModel> getBankDetailsModels = new ArrayList<>();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = BankDetailsListFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        CheckForRequest();


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.add_Bank_Details_Fragment);
            }
        });

    }


    private void CheckForRequest() {

        ApiUtils.getDoctorBankDetails(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<GetBankDetailsModel> getBankDetailsModels1 = (List<GetBankDetailsModel>) obj;

                if (null != getBankDetailsModels1) {
                    if (getBankDetailsModels1.isEmpty()) {
                        navController.navigate(R.id.action_bankDetailsListFragment_to_add_Bank_Details_Fragment);
                    } else {

                        getBankDetailsModels.clear();
                        getBankDetailsModels.addAll(getBankDetailsModels1);
                        // Log.d("TAG", "onSuccess: " + getBankDetailsModels);
                        bankDetailsAdapter = new BankDetailsAdapter(getBankDetailsModels, requireActivity());
                        binding.recyclerView.setAdapter(bankDetailsAdapter);
                        bankDetailsAdapter.notifyDataSetChanged();
                        //     binding.setBankDetails(getBankDetailsModels.get(0));
                    }
                }


//

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(requireActivity(), "" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}