package com.luniva.lunivacaredemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luniva.lunivacaredemo.R;

public class TotalSamplesRejectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_samples_rejected);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
