package com.example.breast_symmetry_evaluation.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breast_symmetry_evaluation.FileControl.FileControl;
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
    private Bitmap imageFile;

    /**
     * 图片存储路径
     */
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_preview_screen);

        readFile();
        initAction();
    }

    /**
     * 初始化动作
     */
    private void initAction() {
        ImageView cancelUpLoad=(ImageView) findViewById(R.id.cancel_upload);
        cancelUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("取消上传");
            }
        });

        TextView upLoadImage=(TextView) findViewById(R.id.upload_image);
        upLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileControl fileControl=new FileControl();
                fileControl.reqUpdImg(imagePath);
            }
        });

    }

    /**
     * 读取文件
     */
    private void readFile() {
        Intent intent=getIntent();
        imagePath=intent.getStringExtra("image");

        imageFile= BitmapFactory.decodeFile(imagePath);
        ImageView imageView=(ImageView) findViewById(R.id.pre_view);
        imageView.setImageBitmap(imageFile);
    }

}