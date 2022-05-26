package com.example.ocrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.meterBtn);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, ScanMeterActivity.class);
            startActivity(intent);
        });


        String licenseKey = getString(R.string.anyline_license_key);

    }


}