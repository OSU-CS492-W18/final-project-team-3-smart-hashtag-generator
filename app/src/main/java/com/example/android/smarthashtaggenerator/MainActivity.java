package com.example.android.smarthashtaggenerator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView mTakePhotoTV;
    private TextView mChoosePhotoTV;
    private TextView mHistoryPhotoTV;

    // For deciding what to do in onActivityResult
    private int ACTIVITYRESULT_ID;

    // For taking photos
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    public static final String EXTRA_TAKE_PHOTO = "Take Photo";

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
                ACTIVITYRESULT_ID = 0;
                /*Intent takePhotoIntent = new Intent(MainActivity.this, TakePhoto.class);
                startActivity(takePhotoIntent);*/
                dispatchTakePictureIntent();
            }
        });

        mChoosePhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTIVITYRESULT_ID = 1;
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


    //upon taking or choosing image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Uri selectedImage = data.getData();

        switch(ACTIVITYRESULT_ID) {

            case 0:
                if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
                    //send image to api
                    File file = new File(mCurrentPhotoPath);
                    if (file.exists()) {
                        /*Bitmap photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        ImageView cameraIV = new ImageView(this);
                        cameraIV.setImageBitmap(photoBitmap);*/
                        Intent takePhotoIntent = new Intent(this, TakePhoto.class);
                        takePhotoIntent.putExtra(EXTRA_TAKE_PHOTO, file);
                        startActivity(takePhotoIntent);
                    }


                }

                break;
            //case from choosing photo:
            case 1:
                if(resultCode == RESULT_OK){
                    //requesting permission
                    //send image to api

                    //send image over to choose photo activity to handle
                    //Intent choosePhotoIntent = new Intent(MainActivity.this, ChoosePhoto.class);
                    //startActivity(choosePhotoIntent);
                }
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                try {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.smarthashtaggenerator.fileprovider",
                            photoFile);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
