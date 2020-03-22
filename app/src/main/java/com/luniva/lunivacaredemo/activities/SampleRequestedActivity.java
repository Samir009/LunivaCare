package com.luniva.lunivacaredemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luniva.lunivacaredemo.R;

public class SampleRequestedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_requested);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
