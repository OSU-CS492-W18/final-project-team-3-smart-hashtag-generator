package com.example.android.smarthashtaggenerator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.smarthashtaggenerator.utils.MicrosoftComputerVisionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ViewHistoryActivity extends AppCompatActivity {

    private SQLiteDatabase mDB;
    private final static String TAG = ViewHistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        DBHelper dbHelper = new DBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        ArrayList<MicrosoftComputerVisionUtils.ComputerVisionItem> items = getAllSavedResults();
        for (MicrosoftComputerVisionUtils.ComputerVisionItem item : items) {
            Log.d(TAG, "URI: " + Uri.fromFile(item.file.getAbsoluteFile()));
            Log.d(TAG, "Tags:" + item.tags.toString());
        }
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
        ArrayList<String> savedTagsList = new ArrayList<String>();
        while (cursor.moveToNext()) {
            Uri fileUri = Uri.parse(cursor.getString(
                    cursor.getColumnIndex(DBContract.SavedResults.COLUMN_PHOTO))
            );
            String allTags = cursor.getString(
                    cursor.getColumnIndex(DBContract.SavedResults.COLUMN_TAGS));
            StringTokenizer stTags = new StringTokenizer(allTags, " ");
            while (stTags.hasMoreTokens()) {
                String token = stTags.nextToken();
                savedTagsList.add(token);
            }
            //savedLocationsList.add(location);
            MicrosoftComputerVisionUtils.ComputerVisionItem item = new MicrosoftComputerVisionUtils.ComputerVisionItem();
            item.file = new File(fileUri.getPath());
            item.tags = savedTagsList;
            savedResultsList.add(item);
        }

        cursor.close();
        return savedResultsList;
    }
}
