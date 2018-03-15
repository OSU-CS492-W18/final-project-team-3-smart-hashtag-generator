package com.example.android.smarthashtaggenerator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smarthashtaggenerator.utils.MicrosoftComputerVisionUtils;

import java.io.File;
import java.util.ArrayList;


//import static com.example.android.smarthashtaggenerator.MainActivity.VISION_TAGS_KEY;
//import static com.example.android.smarthashtaggenerator.MainActivity.getPath;

public class ShowTagsActivity extends AppCompatActivity {
    private ImageView image;
    private TextView hashes;
  //  private String returnedResult;
    private MicrosoftComputerVisionUtils.ComputerVisionItem visionItem;
    private File file;
    private ArrayList<String> tagList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tags);
        image = (ImageView)findViewById(R.id.iv_display_image);
        hashes = (TextView)findViewById(R.id.tv_hash_tags);

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(MainActivity.VISION_OBJECT_KEY))
        {
           /* Uri contentUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (contentUri != null) {
                String pathUri = contentUri.toString();
                file = new File(pathUri);

                tags = intent.getSerializableExtra(VISION_TAGS_KEY);*/
            tagList = new ArrayList<String>();
            visionItem = (MicrosoftComputerVisionUtils.ComputerVisionItem) intent.getSerializableExtra(MainActivity.VISION_OBJECT_KEY);
            file = visionItem.file;
            tagList = visionItem.tags;
            Bitmap photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            image.setImageBitmap(photoBitmap);

        }


    }
}
