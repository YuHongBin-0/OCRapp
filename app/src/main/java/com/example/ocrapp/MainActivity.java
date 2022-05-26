package com.example.ocrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";
    private String lastDetectedBarcodeValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.meterBtn);

//        button.setOnClickListener(v -> {
//            Intent intent = new Intent(this, ScanMeterActivity.class);
//            startActivity(intent);
//        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ScanMeterActivity.class);
                startActivityForResult(intent, 1);

            }
        });




        String licenseKey = getString(R.string.anyline_license_key);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        EditText textView = findViewById(R.id.textView);

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String extraReturn = data.getStringExtra("extra_return");
                    Log.d(TAG, extraReturn);
                    lastDetectedBarcodeValue = extraReturn;
                    textView.setText(lastDetectedBarcodeValue);
                }
                break;
            default:
        }
    }


}