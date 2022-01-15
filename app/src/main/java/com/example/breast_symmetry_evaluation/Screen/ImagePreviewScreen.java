package com.example.breast_symmetry_evaluation.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.breast_symmetry_evaluation.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

public class ImagePreviewScreen extends AppCompatActivity {

    /**
     * 图片文件
     */
    private byte[] imageFile;

    /**
     * 图片存储路径
     */
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview_screen);

        //隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getImageFile();
        saveImageFile();

    }

    /**
     * 存储图片文件
     */
    private void saveImageFile() {
        String uuid = UUID.randomUUID().toString();
        imagePath = getExternalFilesDir(null) + "/" + uuid + ".jpg";
        System.out.println(imagePath);

        File tempFile = new File(imagePath);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(tempFile);
            outputStream.write(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取图片文件
     */
    private void getImageFile() {
        Intent intent = getIntent();
        imageFile = intent.getByteArrayExtra("image");
    }
}