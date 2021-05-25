package com.criteriontech.digidoctormax;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.FragmentHomeQuarantineRequestListBinding;
import com.criteriontech.digidoctormax.databinding.HomeIsolationViewBinding;
import com.criteriontech.digidoctormax.model.HomeIsolationReqModel;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeQuarantineRequestListFragment extends Fragment {
    private static final String TAG = "HomeQuarantineRequestLi";


    NavController navController;
    List<HomeIsolationReqModel> reqModels;
    HomeIsolationAdapter homeIsolationAdapter;
    FragmentHomeQuarantineRequestListBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeQuarantineRequestListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        reqModels = new ArrayList<>();
        homeIsolationAdapter = new HomeIsolationAdapter(reqModels);
        binding.isolationRec.setAdapter(homeIsolationAdapter);
        getIsolationData();

    }

    private void getIsolationData() {
        AppUtils.showRequestDialog(requireActivity());
        LoginValue loginValue = new LoginValue();
        loginValue.setServiceProviderDetailsId(SharedPrefManager.getInstance(requireActivity()).getUser().getId());

        ApiUtils.isolationData(loginValue, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                AppUtils.hideDialog();
                reqModels.clear();
                reqModels.addAll((List<HomeIsolationReqModel>) obj);
                homeIsolationAdapter.notifyDataSetChanged();
                Log.d(TAG, "onSuccess: " + homeIsolationAdapter.getItemCount());
                Log.d(TAG, "onSuccess: " + reqModels.size());
            }

            @Override
            public void onFailure(String msg) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class HomeIsolationAdapter extends RecyclerView.Adapter<HomeIsolationAdapter.IsolationVH> {
        List<HomeIsolationReqModel> isolationReqModels;

        public HomeIsolationAdapter(List<HomeIsolationReqModel> reqModels) {
            this.isolationReqModels = reqModels;
        }

        @NonNull
        @Override
        public IsolationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            HomeIsolationViewBinding isolationViewBinding = HomeIsolationViewBinding.inflate(inflater, parent, false);
            return new IsolationVH(isolationViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull IsolationVH holder, int position) {
            HomeIsolationReqModel homeIsolationReqModel = isolationReqModels.get(position);
            holder.isolationViewBinding.setIsolation(homeIsolationReqModel);

            String status = homeIsolationReqModel.getHomeIsolationStatus();


            if (null != isolationReqModels.get(position) && null != isolationReqModels.get(position).getHomeIsolationStatus()) {
                if (status.equalsIgnoreCase("Pending")) {
                    holder.isolationViewBinding.llImage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow700)));
                } else if (status.equalsIgnoreCase("Approved")) {
                    holder.isolationViewBinding.llImage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green700)));
                } else if (status.equalsIgnoreCase("Declined")) {
                    holder.isolationViewBinding.llImage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
            }

            holder.isolationViewBinding.getRoot().setOnClickListener(v -> {
                HomeQuarantineRequestListFragmentDirections.ActionHomeQuarantineRequestListFragmentToHomeIsolationDetailFragment action = HomeQuarantineRequestListFragmentDirections.actionHomeQuarantineRequestListFragmentToHomeIsolationDetailFragment();
                action.setId(String.valueOf(homeIsolationReqModel.getId()));
                navController.navigate(action);

            });
        }

        @Override
        public int getItemCount() {
            return null == isolationReqModels ? 0 : isolationReqModels.size();
        }

        public class IsolationVH extends RecyclerView.ViewHolder {
            HomeIsolationViewBinding isolationViewBinding;

            public IsolationVH(@NonNull HomeIsolationViewBinding isolationViewBinding) {
                super(isolationViewBinding.getRoot());
                this.isolationViewBinding = isolationViewBinding;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}
