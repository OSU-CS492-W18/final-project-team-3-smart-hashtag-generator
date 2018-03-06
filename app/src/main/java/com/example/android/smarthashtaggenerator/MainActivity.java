package com.example.android.smarthashtaggenerator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTakePhotoTV;
    private TextView mChoosePhotoTV;
    private TextView mHistoryPhotoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTakePhotoTV = findViewById(R.id.take_photo_btn);
        mChoosePhotoTV = findViewById(R.id.choose_photo_btn);
        mHistoryPhotoTV = findViewById(R.id.view_history_btn);

        mTakePhotoTV.getBackground().setAlpha(63);
        mChoosePhotoTV.getBackground().setAlpha(63);
        mHistoryPhotoTV.getBackground().setAlpha(63);

        mTakePhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MainActivity.this, TakePhoto.class);
                startActivity(takePhotoIntent);
            }
        });

        mChoosePhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        mHistoryPhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewHistoryIntent = new Intent(MainActivity.this, ViewHistory.class);
                startActivity(viewHistoryIntent);
            }
        });

    }

    //upon choosing image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {

            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    //send image to api
                }

                break;
            //case from choosing photo:
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    //send image to api
                }
                break;
        }
    }
}
