package com.example.android.smarthashtaggenerator;

import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smarthashtaggenerator.utils.MicrosoftComputerVisionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ViewHistoryActivity extends AppCompatActivity {

    private RecyclerView mItemResultsRV;
    private Adapter mAdapter;

    private ImageView image;
    private TextView hashes;
    private String hashText;
    private MicrosoftComputerVisionUtils.ComputerVisionItem visionItem;
    private File file;
    private ArrayList<String> tagList;
    public ClipboardManager clipboard;

    private SQLiteDatabase mDB;
    private final static String TAG = ViewHistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        mItemResultsRV = findViewById(R.id.rv_item_list);
        mItemResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mItemResultsRV.setHasFixedSize(true);


        DBHelper dbHelper = new DBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        mAdapter = new Adapter();
        mAdapter.updateResults(getAllSavedResults());
        mItemResultsRV.setAdapter(mAdapter);
    }

    private ArrayList<MicrosoftComputerVisionUtils.ComputerVisionItem> getAllSavedResults() {
        Cursor cursor = mDB.query(
                DBContract.SavedResults.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DBContract.SavedResults.COLUMN_TIMESTAMP + " DESC"
        );

        ArrayList<MicrosoftComputerVisionUtils.ComputerVisionItem> savedResultsList = new ArrayList<MicrosoftComputerVisionUtils.ComputerVisionItem>();
        while (cursor.moveToNext()) {
            ArrayList<String> savedTagsList = new ArrayList<String>();
            MicrosoftComputerVisionUtils.ComputerVisionItem item = new MicrosoftComputerVisionUtils.ComputerVisionItem();

            Uri fileUri = Uri.parse(cursor.getString(
                    cursor.getColumnIndex(DBContract.SavedResults.COLUMN_PHOTO))
            );
            item.file = new File(fileUri.getPath());

            String allTags = cursor.getString(
                    cursor.getColumnIndex(DBContract.SavedResults.COLUMN_TAGS));
            StringTokenizer stTags = new StringTokenizer(allTags, " ");
            while (stTags.hasMoreTokens()) {
                String token = stTags.nextToken();
                savedTagsList.add(token);
            }
            //savedLocationsList.add(location);
            item.tags = savedTagsList;
            Log.d(TAG, "tags: " + item.tags.toString());
            savedResultsList.add(item);
            //savedTagsList.clear();
        }

        cursor.close();
        return savedResultsList;
    }
}
