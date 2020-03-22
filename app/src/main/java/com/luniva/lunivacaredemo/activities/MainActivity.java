package com.luniva.lunivacaredemo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.luniva.lunivacaredemo.R;
import com.luniva.lunivacaredemo.databinding.ActivityMainBinding;
import com.luniva.lunivacaredemo.fragments.DashboardFragment;
import com.luniva.lunivacaredemo.fragments.PatientListFragment;
import com.luniva.lunivacaredemo.fragments.SearchFragment;
import com.luniva.lunivacaredemo.helpers.ShowToast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;

    private ActionBarDrawerToggle mToogle;

//    headerview of navigation drawer
    private View headerView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mToogle = new ActionBarDrawerToggle(this, binding.idActivityMain, R.string.open, R.string.close);
        binding.idActivityMain.addDrawerListener(mToogle);
        mToogle.syncState();
//        setSupportActionBar(binding.t);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.smalllogo);
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(icon, 400, 100, true));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(newdrawable);

        binding.navigationView.setNavigationItemSelectedListener(this);

        headerView = binding.navigationView.getHeaderView(0);
        linearLayout = headerView.findViewById(R.id.profile_ll);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                    replace(R.id.FragmentContainer, new DashboardFragment()).commit();
            binding.navigationView.setCheckedItem(R.id.id_dashboard);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfilePage();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(binding.idActivityMain.isDrawerOpen(GravityCompat.START)){
            binding.idActivityMain.closeDrawer(GravityCompat.START);

        } else {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Do you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    private void openProfilePage(){
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.notification_home){
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        }
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.id_dashboard:
                binding.navigationView.setCheckedItem(R.id.id_dashboard);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                        replace(R.id.FragmentContainer, new DashboardFragment()).commit();
                ShowToast.withLongMessage("dashboard clicked.");
                break;

            case R.id.id_search:
                binding.navigationView.setCheckedItem(R.id.id_search);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                        replace(R.id.FragmentContainer, new SearchFragment()).commit();
                ShowToast.withLongMessage("Search clicked.");
                break;

            case R.id.id_patient_list:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                        replace(R.id.FragmentContainer, new PatientListFragment()).commit();
                ShowToast.withLongMessage("Search clicked.");
                break;
        }
        binding.idActivityMain.closeDrawer(GravityCompat.START);
        return true;
    }

}
