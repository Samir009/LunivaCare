package com.luniva.lunivacaredemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luniva.lunivacaredemo.R;

import java.util.Objects;

public class TotalSamplesCollectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_samples_collected);

        getSupportActionBar().setElevation(0);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
