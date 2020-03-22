package com.luniva.lunivacaredemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.luniva.lunivacaredemo.R;
import com.luniva.lunivacaredemo.databinding.ActivityEditProfileBinding;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
