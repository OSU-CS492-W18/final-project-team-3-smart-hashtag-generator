package com.example.android.smarthashtaggenerator.utils;


import android.content.Context;
import android.net.Uri;


import com.example.android.smarthashtaggenerator.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by wongnich on 3/6/18.
 */

public class MicrosoftComputerVisionUtils {

    //final url: https://[location].api.cognitive.microsoft.com/vision/v1.0/analyze[?visualFeatures][&details][&language]
    private final static String MIC_VISION__BASE_URL = "http://westcentralus.api.cognitive.microsoft.com/vision/v1.0/tag";
    private final static String MIC_VISION_VISUAL_FEATURE_PARAM = "?visualFeatures";

    public static class ComputerVisionItem implements Serializable {
        public static final String EXTRA_VISION_ITEM = "com.example.android.smarthashtaggenerator.utils.ComputerVisionItem.Result";
        public String tag;
    }

    public static String buildVisionURL() {
        return Uri.parse(MIC_VISION__BASE_URL).buildUpon()
                .appendQueryParameter(MIC_VISION_VISUAL_FEATURE_PARAM, "Tags")
                .build()
                .toString();
    }

    public static ArrayList<ComputerVisionItem> parseVisionJSON(String visionJSON) {
        try {
            ArrayList<ComputerVisionItem> computerVisionItemList = new ArrayList<ComputerVisionItem>();

            //parse JSON Object
            JSONObject visionObj = new JSONObject(visionJSON);
            JSONArray visionList = visionObj.getJSONArray("tags");

            for (int i = 0; i < visionList.length(); i++) {
                ComputerVisionItem visionItem = new ComputerVisionItem();
                JSONObject visionListElem = visionList.getJSONObject(i);

                visionItem.tag = visionListElem.getString("name");

                computerVisionItemList.add(visionItem);
            }

            return computerVisionItemList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
