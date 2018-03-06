package com.example.android.smarthashtaggenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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

    }


}
