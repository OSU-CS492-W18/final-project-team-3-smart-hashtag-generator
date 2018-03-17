package com.example.android.smarthashtaggenerator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.smarthashtaggenerator.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Owner on 3/4/2018.
 */

public class ComputerVisionLoader extends AsyncTaskLoader<String> {
    private String mVisionURL;
    private String visionResultsJSON;
    private File image;
    private final static String TAG = ComputerVisionLoader.class.getSimpleName();

    public ComputerVisionLoader(Context context, String url, File image) {
        super(context);
        this.mVisionURL = url;
        this.image = image;
    }

    @Override
    public void onStartLoading() {
        if (mVisionURL != null) {
            /*if (visionResultsJSON != null) {
                Log.d(TAG, "using cached tags");
                deliverResult(visionResultsJSON);
            } else {*/
                forceLoad();
            //}
        }
    }

    @Override
    public String loadInBackground() {
        String visionResultsJSON = null;
        if (mVisionURL != null) {
            Log.d(TAG, "loading tags from ComputerVision API using this URL: " + mVisionURL);
            try {
                visionResultsJSON = NetworkUtils.doHTTPGet(mVisionURL, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return visionResultsJSON;
    }

    @Override
    public void deliverResult(@Nullable String data) {
        visionResultsJSON = data;
        super.deliverResult(data);
    }
}
