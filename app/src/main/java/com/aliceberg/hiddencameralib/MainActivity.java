package com.aliceberg.hiddencameralib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.HiddenCameraService;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import java.io.File;

public class MainActivity extends AppCompatActivity{



    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn);

        requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        requestPermissions(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, 102);








    }

    public void btnClick(View view){

        Log.e("TAG", "btnClick: HERE" );
        startService(new Intent(this, HiddenCamService.class));
        Log.e("TAG", "btnClick: END" );
    }



}