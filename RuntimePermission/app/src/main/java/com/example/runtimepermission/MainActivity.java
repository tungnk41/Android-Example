package com.example.runtimepermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnRequestPer;
    private Button btnOpenPer;
    private final int REQUEST_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenPer = findViewById(R.id.btnOpenPer);
        btnRequestPer = findViewById(R.id.btnRequestPer);

        btnOpenPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpenPer();
            }
        });

        btnRequestPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clickRequestPer();
                requestPermissionLibrary();
            }
        });
    }

    private void clickRequestPer() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else {
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE};
            requestPermissions(permission,REQUEST_PERMISSION_CODE);
        }
    }

    private void clickOpenPer() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_CODE){
            if(grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //3rd Library : TedPermission
    private void requestPermissionLibrary(){
        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .setDeniedTitle("Open Setting")
                .setDeniedMessage("Go to Setting -> Permission")
                .check();
    }

}