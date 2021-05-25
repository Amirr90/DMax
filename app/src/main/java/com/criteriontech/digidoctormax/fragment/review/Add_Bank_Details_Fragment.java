package com.criteriontech.digidoctormax.fragment.review;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.AddBankDetailsFragmentBinding;
import com.criteriontech.digidoctormax.databinding.FragmentRescheduleBinding;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.model.ChatModel;
import com.criteriontech.digidoctormax.model.GetBanModel;
import com.criteriontech.digidoctormax.model.GetBankDetailsModel;
import com.criteriontech.digidoctormax.model.UpdateBankModel;
import com.criteriontech.digidoctormax.model.User;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.collections.IndexedValue;

import static com.criteriontech.digidoctormax.utils.AppUtils.getBankNameList;


public class Add_Bank_Details_Fragment extends Fragment {

    private static final String PENDING = "0";
    private static final String APPROVED = "1";
    private static final String REJECTED = "2";
    private static final String NOT_REQUESTED = "12";
    AddBankDetailsFragmentBinding addBankDetailsFragmentBinding;

    List<String> bankNameList;

    NavController nav;

    String AcholderName, IFSC, AcNo, tag, bank, id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addBankDetailsFragmentBinding = AddBankDetailsFragmentBinding.inflate(getLayoutInflater());
        return addBankDetailsFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);

        bankNameList = getBankNameList();
        showAddBankDetailsPage();

        if (null == getArguments())
            tag = NOT_REQUESTED;


        else {
            AcholderName = getArguments().getString("Name");
            IFSC = getArguments().getString("ifsc");
            AcNo = getArguments().getString("AcNo");
            tag = getArguments().getString("isapproved");
            bank = getArguments().getString("bank");
            id = getArguments().getString("ID");
            for (int a = 0; a < bankNameList.size(); a++) {
                if (bankNameList.get(a).equals(bank)) {
                    addBankDetailsFragmentBinding.spinnerbankname.setSelection(a);

                }


            }

        }


        addBankDetailsFragmentBinding.etaccountholder.setText(AcholderName);
        addBankDetailsFragmentBinding.etaccountno.setText(AcNo);
        addBankDetailsFragmentBinding.etifsc.setText(IFSC);
        addBankDetailsFragmentBinding.tvGetStart.setTag(tag);
        // addBankDetailsFragmentBinding.spinnerbankname.setSelection();


        addBankDetailsFragmentBinding.tvGetStart.setEnabled(!tag.equals(APPROVED));

        addBankDetailsFragmentBinding.etifsc.setEnabled(!tag.equals(APPROVED));
        addBankDetailsFragmentBinding.etaccountno.setEnabled(!tag.equals(APPROVED));
        addBankDetailsFragmentBinding.etaccountholder.setEnabled(!tag.equals(APPROVED));
        addBankDetailsFragmentBinding.spinnerbankname.setEnabled(!tag.equals(APPROVED));

        addBankDetailsFragmentBinding.tvGetStart.setText(tag.equals(PENDING) ? "Delete Request" : tag.equals(APPROVED) ? "Approved" : tag.equals(REJECTED) ? "Rejected" : "Save Details");
        addBankDetailsFragmentBinding.tvGetStart.setOnClickListener(v -> {
            String tag = (String) addBankDetailsFragmentBinding.tvGetStart.getTag();
            if (tag.equals(PENDING))
                deletebankdetailsrequest();
            else if (tag.equals(NOT_REQUESTED))
                if (isValidate()) {
                    addBankDetails();
                }

        });

    }


    private void deletebankdetailsrequest() {
        AppUtils.showRequestDialog(requireActivity());
        GetBankDetailsModel getBankDetailsModel = new GetBankDetailsModel();
        getBankDetailsModel.setId(Integer.valueOf(id));

        ApiUtils.deleteBankDetails(getBankDetailsModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                DrHomeActivity.getInstance().onSupportNavigateUp();

            }

            @Override
            public void onFailure(String msg) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidate() {

        if (TextUtils.isEmpty(addBankDetailsFragmentBinding.etaccountno.getText().toString())) {
            Toast.makeText(requireActivity(), "Fill Account Number", Toast.LENGTH_SHORT).show();
            addBankDetailsFragmentBinding.etaccountno.setError("Fill Account Number");
            return false;
        } else if (TextUtils.isEmpty(addBankDetailsFragmentBinding.etaccountholder.getText())) {
            Toast.makeText(requireActivity(), "Enter Account holder Name!", Toast.LENGTH_SHORT).show();
            addBankDetailsFragmentBinding.etaccountholder.setError("Enter Account holder Name");
            return false;
        } else if (TextUtils.isEmpty(addBankDetailsFragmentBinding.spinnerbankname.getSelectedItem().toString())) {
            Toast.makeText(requireActivity(), "Select Bank Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(addBankDetailsFragmentBinding.etifsc.getText().toString())) {
            Toast.makeText(requireActivity(), "Enter Ifsc code", Toast.LENGTH_SHORT).show();
            addBankDetailsFragmentBinding.etifsc.setError("Enter Ifsc code");
            return false;
        } else {
            return true;
        }

    }


    private void addBankDetails() {

        UpdateBankModel updateBankModel = new UpdateBankModel();

        updateBankModel.setBankName(addBankDetailsFragmentBinding.spinnerbankname.getSelectedItem().toString());
        updateBankModel.setServiceProviderDetailsId(SharedPrefManager.getInstance(getActivity()).getUser().getId());
        updateBankModel.setIfsc(addBankDetailsFragmentBinding.etifsc.getText().toString());
        updateBankModel.setAccountNo(addBankDetailsFragmentBinding.etaccountno.getText().toString());
        updateBankModel.setAccountHolder(addBankDetailsFragmentBinding.etaccountholder.getText().toString());


        ApiUtils.updateBankData(updateBankModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                Toast.makeText(requireActivity(), "Bank Details Added Successfully!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(requireActivity(), "something wrong try again" + msg, Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void showAddBankDetailsPage() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, bankNameList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addBankDetailsFragmentBinding.spinnerbankname.setAdapter(arrayAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}