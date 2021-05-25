package com.criteriontech.digidoctormax.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.BankDetailsListFragmentBinding;
import com.criteriontech.digidoctormax.databinding.BankDetailsViewBinding;
import com.criteriontech.digidoctormax.databinding.DrugInteractionViewBinding;
import com.criteriontech.digidoctormax.fragment.BankDetailsListFragment;
import com.criteriontech.digidoctormax.fragment.home.DrHomeActivity;
import com.criteriontech.digidoctormax.model.DrugIntrcationModel;
import com.criteriontech.digidoctormax.model.GetBankDetailsModel;

import java.util.List;

public class BankDetailsAdapter extends RecyclerView.Adapter<BankDetailsAdapter.ViewHolderVh> {


    private List<GetBankDetailsModel> getBankDetailsModels;
    private Activity ctx;
    BankDetailsViewBinding bankDetailsViewBinding;
    NavController navController;


    public BankDetailsAdapter(List<GetBankDetailsModel> getBankDetailsModels, Activity ctx) {
        this.getBankDetailsModels = getBankDetailsModels;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolderVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bankDetailsViewBinding = BankDetailsViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        navController = Navigation.findNavController(parent);
        return new BankDetailsAdapter.ViewHolderVh(bankDetailsViewBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVh holder, int position) {
        GetBankDetailsModel getBankDetailsModel = getBankDetailsModels.get(position);


        if (getBankDetailsModel.getIsApproved().equals(1)) {
            holder.bankDetailsViewBinding.imageView51.setBackgroundResource(R.drawable.aprrovad);
        }

        if (getBankDetailsModel.getIsApproved().equals(0)) {
            holder.bankDetailsViewBinding.imageView51.setBackgroundResource(R.drawable.aprovalpending);
        }
        if (getBankDetailsModel.getIsApproved().equals(2)) {
            holder.bankDetailsViewBinding.imageView51.setBackgroundResource(R.drawable.rejected);
        }


        holder.bankDetailsViewBinding.tvdoctor2.setText(getBankDetailsModel.getRequestTime());
        holder.bankDetailsViewBinding.tvdoctor3.setText(getBankDetailsModel.getAccountHolder());
        holder.bankDetailsViewBinding.tvdoctor4.setText(getBankDetailsModel.getAccountNo());


        holder.bankDetailsViewBinding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("ID", String.valueOf(getBankDetailsModel.getId()));
                bundle.putString("Name", getBankDetailsModel.getAccountHolder());
                bundle.putString("AcNo", getBankDetailsModel.getAccountNo());
                bundle.putString("ifsc", getBankDetailsModel.getIfsc());
                bundle.putString("bank", getBankDetailsModel.getBankName());
                bundle.putString("isapproved", String.valueOf(getBankDetailsModel.getIsApproved()));
                navController.navigate(R.id.add_Bank_Details_Fragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getBankDetailsModels.size();
    }

    public class ViewHolderVh extends RecyclerView.ViewHolder {

        BankDetailsViewBinding bankDetailsViewBinding;

        public ViewHolderVh(@NonNull BankDetailsViewBinding itemView) {
            super(itemView.getRoot());
            this.bankDetailsViewBinding = itemView;
        }
    }
}
