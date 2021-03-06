package com.example.ocrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//import io.anyline.examples.R;
//import io.anyline.examples.SettingsFragment;

import at.nineyards.anyline.core.LicenseException;
import io.anyline.AnylineSDK;

import io.anyline.plugin.ScanResultListener;
import io.anyline.plugin.meter.MeterScanMode;
import io.anyline.plugin.meter.MeterScanResult;
import io.anyline.plugin.meter.MeterScanViewPlugin;
import io.anyline.view.BaseScanViewConfig;
import io.anyline.view.ScanView;
import io.anyline.view.ScanViewPluginConfig;

public class ScanMeterActivity extends AppCompatActivity{

    protected ScanView energyScanView;
    protected String foundBarcodeString;
    private String lastDetectedBarcodeValue = "";

    private static final String TAG = "SecondActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_meter);

        Button buttonBack = findViewById(R.id.backBtn);

        try {
            AnylineSDK.init(getString(R.string.anyline_license_key), this);
        } catch (LicenseException e) {
        }

        lastDetectedBarcodeValue = "";

        // get the view from the layout (check out the xml for the configuration of the view)
        energyScanView = (ScanView) findViewById(R.id.scan_view);
        // ANALOG_METER will work for all types of analog meters (gas, electric, water) and
        // automatically detects digits before and after the point

        ScanViewPluginConfig energyScanviewPluginConfig = new ScanViewPluginConfig(getApplicationContext(), "energy_view_config.json");

        MeterScanViewPlugin scanViewPlugin = new MeterScanViewPlugin(getApplicationContext(), energyScanviewPluginConfig, "METER");

        BaseScanViewConfig energyBaseScanViewConfig = new BaseScanViewConfig(getApplicationContext(), "energy_view_config.json");
        //set the base scanViewConfig to the ScanView
        energyScanView.setScanViewConfig(energyBaseScanViewConfig);

        energyScanView.setScanViewPlugin(scanViewPlugin);

        TextView tView = findViewById(R.id.tView);

        scanViewPlugin.addScanResultListener(new ScanResultListener<MeterScanResult>() {
            @Override
            public void onResult(MeterScanResult energyResult) {
                new SimpleAlertDialog(ScanMeterActivity.this)
                        .setMessage(energyResult.getResult())
                        .show();

                lastDetectedBarcodeValue = energyResult.getResult();
                tView.setText(energyResult.getResult());
            }
        });

//        Below codes for manual return to previous activity with result data

//        buttonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("extra_return", lastDetectedBarcodeValue);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });



        ((MeterScanViewPlugin)energyScanView.getScanViewPlugin()).setScanMode(MeterScanMode.AUTO_ANALOG_DIGITAL_METER);
        
        
    }


    @Override
    protected void onResume() {
        super.onResume();
        //start the actual scanning
        energyScanView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stop the scanning
        energyScanView.stop();
        //release the camera (must be called in onPause, because there are situations where
        // it cannot be auto-detected that the camera should be released)
        energyScanView.releaseCameraInBackground();
    }






}