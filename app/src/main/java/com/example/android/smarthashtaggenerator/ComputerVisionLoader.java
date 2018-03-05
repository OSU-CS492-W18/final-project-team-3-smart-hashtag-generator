package com.example.android.smarthashtaggenerator;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Owner on 3/4/2018.
 */

public class ComputerVisionLoader extends AsyncTaskLoader<String> {
    private String  url;
    String resultsJSON;
    ComputerVisionLoader(Context context, String url) {
        super(context);
        url = url;
    }
    @Override
    public String loadInBackground() {
        return null;
    }
}
