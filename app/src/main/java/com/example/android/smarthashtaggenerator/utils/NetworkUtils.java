package com.example.android.smarthashtaggenerator.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wongnich on 3/6/18.
 */

public class NetworkUtils {

    private final static String MIC_VISION_APPID_PARAM = "Ocp-Apim-Subscription-Key";
    private final static String MIC_VISION_CONTENT_TYPE_PARAM = "Content-Type";


    private final static String MIC_VISION_CONTENT_TYPE = "application/octet-stream";
    //API key expires March 16, 2018
    private final static String MIC_VISION_APPID = "ec3bec6497eb490c8d2fa03e664660e2";

    private static final OkHttpClient mHTTPClient = new OkHttpClient();



    public static String doHTTPGet(String url) throws IOException {
        Request request = new Request.Builder()
                .header(MIC_VISION_CONTENT_TYPE_PARAM, MIC_VISION_CONTENT_TYPE)
                .header(MIC_VISION_APPID_PARAM, MIC_VISION_APPID)
                .url(url)
                .build();
        Response response = mHTTPClient.newCall(request).execute();

        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }
}