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

    private String IS_CAMERA_AVAILABLE = "isCameraAvailable";

    Handler cameraHandler;
    Button button;

    SharedPreferences cameraPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraPrefs = getSharedPreferences("cameraPrefs", MODE_PRIVATE);

        button = (Button) findViewById(R.id.btn);

        requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        requestPermissions(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, 102);

        final SharedPreferences.Editor editor = cameraPrefs.edit();
        editor.putBoolean(IS_CAMERA_AVAILABLE, false); //false is default
        editor.apply();

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        mCameraCallback = new CameraManager.AvailabilityCallback() {
            @Override
            public void onCameraAvailable(String cameraId) {
                super.onCameraAvailable(cameraId);
                //Do your work
                editor.putBoolean(IS_CAMERA_AVAILABLE, true);
                editor.apply();
                Log.e("TAG", "OFF");
            }

            @Override
            public void onCameraUnavailable(String cameraId) {
                super.onCameraUnavailable(cameraId);
                //Do your work
                editor.putBoolean(IS_CAMERA_AVAILABLE, false);
                editor.apply();
                Log.e("TAG", "ON");
            }
        };

        mCameraManager.registerAvailabilityCallback((CameraManager.AvailabilityCallback) mCameraCallback, cameraHandler);
    }

    public void btnClick(View view) {

        boolean isCameraAvailable = cameraPrefs.getBoolean(IS_CAMERA_AVAILABLE, false);
        if(isCameraAvailable){
            startService(new Intent( MainActivity.this, HiddenCameraService.class));
        }else{
            Log.e("TAG", "Camera is not available");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraManager.unregisterAvailabilityCallback((CameraManager.AvailabilityCallback) mCameraCallback);
    }
}