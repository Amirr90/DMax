package com.criteriontech.digidoctormax.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityDrHomeBinding;
import com.criteriontech.digidoctormax.databinding.InnerDrawerBinding;
import com.criteriontech.digidoctormax.model.DrawerModel;
import com.criteriontech.digidoctormax.model.LoginValue;
import com.criteriontech.digidoctormax.utils.BaseActivity;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.grisoftware.updatechecker.GoogleChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DrHomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static DrHomeActivity instance;
    ActivityDrHomeBinding binding;
    List<DrawerModel> drawerList;
    NavController navController;

    public static DrHomeActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_home);


        navController = Navigation.findNavController(this, R.id.nav_dr_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController);


        instance = this;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
        binding.setLogin(SharedPrefManager.getInstance(this).getUser());
        binding.tvEdit.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("login", true);
            navController.navigate(R.id.drCreateProfile3, bundle);
            binding.drawerLayout.close();
        });

        drawerList = new ArrayList<>();
        drawerList.add(new DrawerModel(R.drawable.ic_user_blue_icon, getString(R.string.change_password)));
        drawerList.add(new DrawerModel(R.drawable.bankimage, "Update Bank Details"));
        drawerList.add(new DrawerModel(R.drawable.ic_baseline_share_24, "Share App"));
        drawerList.add(new DrawerModel(R.drawable.ic_logout, getString(R.string.logout)));
        try {
            int id = SharedPrefManager.getInstance(this).getUser().getId();
            if (id !=2452)
                drawerList.add(new DrawerModel(R.drawable.ic_virus, "Quarantine Patients"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e.getLocalizedMessage());
        }


        binding.rvDrawer.setAdapter(new DrawerAdp(drawerList));
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForUpdate();
    }

    private void checkForUpdate() {
        new GoogleChecker("com.criteriontech.digidoctormax", DrHomeActivity.this, true, "en");
    }

    public void updateProfile(LoginValue loginValue) {
        binding.setLogin(loginValue);
    }

    public void openDrawer() {
        binding.drawerLayout.open();
    }

    private void showLogoutDialog() {

        String msg = "Do you really want Logout?";
        new AlertDialog.Builder(mActivity).setMessage(msg)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    SharedPrefManager.getInstance(mActivity).clear();
                    navController.navigate(R.id.action_drHome_to_splashScreen);
                    finish();
                }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss()).show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    private class DrawerAdp extends RecyclerView.Adapter<DrawerAdp.ViewHolder> {
        List<DrawerModel> drawerList;

        public DrawerAdp(List<DrawerModel> drawerList) {
            this.drawerList = drawerList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            InnerDrawerBinding innerDrawerBinding = InnerDrawerBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(innerDrawerBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.innerDrawerBinding.setDrawer(drawerList.get(position));
            holder.innerDrawerBinding.imageView13.setImageResource(drawerList.get(position).getImage());
            holder.innerDrawerBinding.getRoot().setOnClickListener(view -> {
                if (position == 0)
                    navController.navigate(R.id.action_drHome_to_changePassword);
                if (position == 1) {
                    navController.navigate(R.id.bankDetailsListFragment);
                }
                if (position == 2)
                    openShareAppDialog("https://play.google.com/store/apps/details?id=com.criteriontech.digidoctormax", DrHomeActivity.this);
                if (position == 3)
                    showLogoutDialog();
                if (position == 4) {
                    navController.navigate(R.id.quarantinePatientListFragment);
                }


                binding.drawerLayout.close();
            });
            binding.drawerLayout.close();
        }

        @Override
        public int getItemCount() {
            Log.v("dfdsf", String.valueOf(drawerList.size()));
            return drawerList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            InnerDrawerBinding innerDrawerBinding;

            public ViewHolder(InnerDrawerBinding innerDrawerBinding) {
                super(innerDrawerBinding.getRoot());
                this.innerDrawerBinding = innerDrawerBinding;
            }
        }
    }

    public static void openShareAppDialog(String link, Activity activity) {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, link);
        activity.startActivity(Intent.createChooser(intent2, "Share via"));
    }
}