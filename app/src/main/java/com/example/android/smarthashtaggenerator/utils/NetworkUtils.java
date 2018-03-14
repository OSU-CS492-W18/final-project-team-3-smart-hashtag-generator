package com.example.android.smarthashtaggenerator.utils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wongnich on 3/6/18.
 */

public class NetworkUtils {

    private final static String MIC_VISION_APPID_PARAM = "Ocp-Apim-Subscription-Key";
    private final static String MIC_VISION_CONTENT_TYPE_PARAM = "Content-Type";


    private final static String MIC_VISION_CONTENT_TYPE = "application/octet-stream";
    //API key expires March 16, 2018
    private final static String MIC_VISION_APPID = "a81cd183c3234e4d959beb1651e3116e";

    private static final OkHttpClient mHTTPClient = new OkHttpClient();



    public static String doHTTPGet(String url, File image) throws IOException {


        //build the image into the url
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file", image.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), image))
                .build();


        //build the whole url
        Request request = new Request.Builder()
                .header(MIC_VISION_CONTENT_TYPE_PARAM, MIC_VISION_CONTENT_TYPE)
                .header(MIC_VISION_APPID_PARAM, MIC_VISION_APPID)
                .url(url)
                .post(requestBody)
                .build();
        Response response = mHTTPClient.newCall(request).execute();

        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }
}