package com.example.breast_symmetry_evaluation.FileControl;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Name:FileControl
 *
 * @Author: Montana
 * @Date:2022-1-9
 * @Description: Control data flowing,get data from server,
 *              upload images to server and get judge result
 *
 */

public class FileControl {
    // Define pic cache dir
    private static  String picUri = Environment.getExternalStorageDirectory() + "/temp.png";

    // Define server url
    private static String localhost = "http://192.168.247.1:8081/";
    private static String getAllData = "record/getAll";
    private static String getSpcData = "record/getRecord";
    private static String postImg = "record/addRecord";

    // Define post method header
    public static final MediaType JSON
            = MediaType.Companion.parse("application/json; charset=utf-8");
    public static final  MediaType FILE
            = MediaType.Companion.parse("text/x-markdown;charset=utf8");

    private String TAG = "tag";

    public FileControl(){}

    /**
     *  OPEN INTERFACE FOR REQUEST
     */

    public void reqAllData(){
        asyncGetData((localhost + getAllData));
    }

    public void reqSpcData(String id){
        asyncPostData((localhost + getSpcData), id);
    }

    public void reqUpdImg(String picUrl){
        uploadImg((localhost + postImg),picUrl);
    }



    /**
     * Convert bitmap to file and return path

     *
     * @param bitmap
     * @return tmpUri
     */
    private String getCurImg(Bitmap bitmap){
        String tmpUri = "";
        try {
            // Create Byte stream for write file
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Convert bitmap to Byte stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            // Get a folder to store our file
            tmpUri = Environment.getExternalStorageDirectory() + "/temp.png";
            File file = new File(tmpUri);

            try {
                file.createNewFile();

                // Convert Byte stream to Byte[]
                byte[] bitmapdata = baos.toByteArray();

                // Write into our created file
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);

                fos.flush();
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("tmpUri", tmpUri);

        return tmpUri;
    }


    /**
     * Upload images to the server
     *
     * @param url       Server url
     * @param picUrl    Pic storage url
     */
    private void uploadImg(String url,String picUrl){
        OkHttpClient okHttpClient = new OkHttpClient();

        File file = null;

        // Get file path and check
        // String fileUri = getCurImg(bitmap);

        if(picUrl == ""){
            Log.e("Error1:", "Get file path error");

            return;
        }else{
            // Open the file to be uploaded
            file = new File(picUrl);

            if(file == null){
                Log.e(TAG,"Get pic file fail");
                return;
            }

            // Add file to File body
            RequestBody fileBody = RequestBody.Companion.create(file, FILE);

            // Create multipart file
            MultipartBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("imgFile",file.getName(),fileBody)
                    .build();

            // Create request
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // Async post image to the server
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e("tag","onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = response.body().string();
                    Log.d(TAG, "response" + message.obj.toString());
                }
            });

            return;
        }
    }

    /**
     * Async get record from DATABASE by id
     *
     * @method: POST
     * @param url
     * @param id
     */

    private void asyncPostData(String url,String id){

        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("id",id)
                .build();

        Log.v("body", body.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = Message.obtain();
                message.what = 0;
                message.obj = e.getMessage();
                Log.d(TAG, "response" + message.obj.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = Message.obtain();
                message.what = 1;
                message.obj = response.body().string();
                Log.d(TAG, "response" + message.obj.toString());
            }
        });
    }

    /**
     * Async get all records from DATABASE
     *
     * @Method: GET
     * @param url
     */

    private void asyncGetData(String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = Message.obtain();
                message.what = 0;
                message.obj = e.getMessage();
                Log.d(TAG,"onFailure:" + message.obj.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = Message.obtain();
                message.what = 1;
                message.obj = response.body().string();
                Log.d(TAG, "response" + message.obj.toString());
            }
        });

    }

}

