package com.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionStatusLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionStatusStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionStatusLocation == PackageManager.PERMISSION_GRANTED && permissionStatusStorage==PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(MainMenu.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        }

        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.backgroudmenu);


    }

    public void addNewViolation(View view) {
        Intent newViolation=new Intent(MainMenu.this,AddingAParkingIntrude.class);
        startActivity(newViolation);
    }

    public void openHistory(View view) {
        Intent newViolation=new Intent(MainMenu.this,HistoryOfViolations.class);
        startActivity(newViolation);
    }
}