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

import com.example.breast_symmetry_evaluation.Camera.CameraActivity;
import com.example.breast_symmetry_evaluation.FileControl.FileControl;
import com.example.breast_symmetry_evaluation.MainActivity;
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

    /**
     * 上一层页面
     */
    private int front;

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

        getExtra();
        readFile();
        initAction();
    }

    private void getExtra() {
        Intent intent=getIntent();
        imagePath=intent.getStringExtra("image");
        front=intent.getIntExtra("front",2);
    }

    /**
     * 初始化动作
     */
    private void initAction() {
        ImageView cancelUpLoad=(ImageView) findViewById(R.id.cancel_upload);
        cancelUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(front==1){
                    Intent intent=new Intent(ImagePreviewScreen.this, CameraActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(ImagePreviewScreen.this, MainActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
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


        imageFile= BitmapFactory.decodeFile(imagePath);
        ImageView imageView=(ImageView) findViewById(R.id.pre_view);
        imageView.setImageBitmap(imageFile);
    }

}