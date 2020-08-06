package com.aliceberg.hiddencameralib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.HiddenCameraService;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private CameraManager mCameraManager;
    private Object mCameraCallback;

    Handler cameraHandler;

    Boolean isCameraAvailable = false;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn);

        requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        requestPermissions(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, 102);

        SharedPreferences.Editor editor = getSharedPreferences("cameraPrefs", MODE_PRIVATE).edit();
        editor.putBoolean("isCameraAvailable", isCameraAvailable);
        editor.apply();

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        mCameraCallback = new CameraManager.AvailabilityCallback() {
            @Override
            public void onCameraAvailable(String cameraId) {
                super.onCameraAvailable(cameraId);
                //Do your work
                Log.e("TAG", "OFF");
            }

            @Override
            public void onCameraUnavailable(String cameraId) {
                super.onCameraUnavailable(cameraId);
                //Do your work

                Log.e("TAG", "ON");
            }
        };

        mCameraManager.registerAvailabilityCallback((CameraManager.AvailabilityCallback) mCameraCallback, cameraHandler);
    }

    public void btnClick(View view) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraManager.unregisterAvailabilityCallback((CameraManager.AvailabilityCallback) mCameraCallback);
    }
}