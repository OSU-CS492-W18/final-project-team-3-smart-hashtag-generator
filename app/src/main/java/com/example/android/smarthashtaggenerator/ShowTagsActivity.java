package com.example.android.smarthashtaggenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smarthashtaggenerator.utils.MicrosoftComputerVisionUtils;

import java.io.File;
import java.util.ArrayList;

public class ShowTagsActivity extends AppCompatActivity {
    private ImageView image;
    private TextView hashes;
    private String hashText;
    private MicrosoftComputerVisionUtils.ComputerVisionItem visionItem;
    private File file;
    private ArrayList<String> tagList;
    public ClipboardManager clipboard;

    private final static String TAG = ShowTagsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tags);
        image = (ImageView)findViewById(R.id.iv_display_image);
        hashes = (TextView)findViewById(R.id.tv_hash_tags);
        hashText = "";
        clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(MainActivity.VISION_OBJECT_KEY))
        {
            tagList = new ArrayList<String>();
            visionItem = (MicrosoftComputerVisionUtils.ComputerVisionItem) intent.getSerializableExtra(MainActivity.VISION_OBJECT_KEY);
            file = visionItem.file;
            tagList = visionItem.tags;
            Bitmap photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            image.setImageBitmap(photoBitmap);
            for (String item : tagList) {
                hashText += item + " ";
            }
            Log.d(TAG, Uri.fromFile(file.getAbsoluteFile()).toString());
            hashes.setText(hashText);
        }

        /*hashes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("simple text", hashText);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Hashtags have been copied to the clipboard!", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.showtags_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareForecast();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareForecast() {
        if (image != null && hashText != null) {
            /*String shareText = "Weather for " + mForecastLocation +
                    ", " + DATE_FORMATTER.format(mForecastItem.dateTime) +
                    ": " + mForecastItem.temperature + mTemperatureUnitsAbbr +
                    " - " + mForecastItem.description +
                    " " + FORECAST_HASHTAG;*/
            /*ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(hashText)
                    .setChooserTitle(R.string.share_chooser_title)
                    .startChooser();*/
            ClipData clip = ClipData.newPlainText("simple text", hashText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Hashtags have been copied to the clipboard!", Toast.LENGTH_LONG).show();

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sharingIntent.setType("image/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file.getAbsoluteFile()));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, hashText);
            startActivity(Intent.createChooser(sharingIntent, "Share with"));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
