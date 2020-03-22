package com.luniva.lunivacaredemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luniva.lunivacaredemo.R;

public class SampleCollectedHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_collected_history);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
