package com.criteriontech.digidoctormax.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ActivityHomeBinding;
import com.criteriontech.digidoctormax.databinding.InnerDrawerBinding;
import com.criteriontech.digidoctormax.model.DrawerModel;
import com.criteriontech.digidoctormax.utils.BaseActivity;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;
import com.grisoftware.updatechecker.GoogleChecker;

import java.util.ArrayList;
import java.util.List;

public class ClinicHomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding binding;
    NavController navController;
    List<DrawerModel> drawerList;
    public static ClinicHomeActivity instance;
    NavigationView navigationView;
    DrawerLayout drawer;

    public static ClinicHomeActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        navController = Navigation.findNavController(this, R.id.nav_clinic_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController);


        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.setLogin(SharedPrefManager.getInstance(this).getUser());
        binding.tvEdit.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("login", true);
            navController.navigate(R.id.createProfile2, bundle);
            binding.drawerLayout.close();
        });
        binding.navView.setNavigationItemSelectedListener(this);
        drawerList = new ArrayList<>();
        drawerList.add(new DrawerModel(R.drawable.ic_comment, getString(R.string.add_new_doctor)));
        drawerList.add(new DrawerModel(R.drawable.ic_wallet_filled_money_tool, getString(R.string.total_collected_fees)));
        drawerList.add(new DrawerModel(R.drawable.ic_waiting_room_sign, getString(R.string.total_patients_visited)));
        drawerList.add(new DrawerModel(R.drawable.ic_waiting_room_sign, getString(R.string.change_password)));
        drawerList.add(new DrawerModel(R.drawable.ic_waiting_room_sign, getString(R.string.home_quarantine_req)));
        //   drawerList.add(new DrawerModel(R.drawable.ic_waiting_room_sign, getString(R.string.share_app)));
        drawerList.add(new DrawerModel(R.drawable.ic_logout, getString(R.string.logout)));

        binding.rvDrawer.setAdapter(new DrawerAdp(drawerList));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        checkForUpdate();
    }

    private void checkForUpdate() {
        new GoogleChecker("com.criteriontech.digidoctormax", ClinicHomeActivity.this, true, "en");
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
                if (position == 0) {
                    navController.navigate(R.id.action_nav_account_to_addNewDocByClinicFragment);
                }
                if (position == 1)
                    navController.navigate(R.id.totalCollectedFees);
                if (position == 2)
                    navController.navigate(R.id.totalPatientVisited);
                if (position == 3)
                    navController.navigate(R.id.changePassword2);
                if (position == 4) {
                    navController.navigate(R.id.homeQuarantineRequestListFragment);
                }
                if (position == 5) {
                    showLogoutDialog();
                }
                binding.drawerLayout.close();
            });
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

    private void showLogoutDialog() {

        String msg = "Do you really want Logout?";
        new AlertDialog.Builder(mActivity).setMessage(msg)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    SharedPrefManager.getInstance(mActivity).clear();
                    navController.navigate(R.id.startActivity2);
                    finish();
                }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss()).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}