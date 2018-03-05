package com.example.android.smarthashtaggenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mTakePhotoBtn;
    private Button mChoosePhotoBtn;
    private Button mHistoryPhotoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTakePhotoBtn = findViewById(R.id.take_photo_btn);
        mChoosePhotoBtn = findViewById(R.id.choose_photo_btn);
        mHistoryPhotoBtn = findViewById(R.id.view_history_btn);

        mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MainActivity.this, TakePhoto.class);
                startActivity(takePhotoIntent);
            }
        });

        mChoosePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choosePhotoIntent = new Intent(MainActivity.this, ChoosePhoto.class);
                startActivity(choosePhotoIntent);
            }
        });

        mHistoryPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewHistoryIntent = new Intent(MainActivity.this, ViewHistory.class);
                startActivity(viewHistoryIntent);
            }
        });

    }


}
