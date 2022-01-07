package com.example.breast_symmetry_evaluation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import Components.HomeFragment;
import Components.InfoFragment;
import Components.StartFragment;
import Screen.InfoScreen;


public class MainActivity extends AppCompatActivity {

    private Button cameraBt;
    private Button photoBt;
    private ImageView camereIv;
    private ImageView photoIv;
    private String TAG = "tag";

    private BottomNavigationBar bottomNavigationBar;
    private int lastSelectedPosition = 0;
    private StartFragment startFragment;
    private InfoFragment infoFragment;
    private HomeFragment homeFragment;

    //需要的权限数组 读/写/相机
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //隐藏状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor(R.color.black)
                .setInActiveColor(R.color.black)
                .setBarBackgroundColor(R.color.white);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.main, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.info, "信息"))
                .addItem(new BottomNavigationItem(R.drawable.home, "我的"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                System.out.println("选择的item为" + position);
                if (position == 0) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    startFragment = new StartFragment();
                    transaction.replace(R.id.content, startFragment);
                    transaction.commit();
                } else if (position == 1) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    infoFragment = new InfoFragment();
                    transaction.replace(R.id.content, infoFragment);
                    transaction.commit();
                } else if (position == 2) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    homeFragment = new HomeFragment();
                    transaction.replace(R.id.content, homeFragment);
                    transaction.commit();
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        setDefaultFragment();

        //initView();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        startFragment = new StartFragment();
        transaction.replace(R.id.content, startFragment);
        transaction.commit();

    }

    private Uri ImageUri;
    public static final int TAKE_PHOTO = 101;
    public static final int TAKE_CAMARA = 100;

    private void initView() {
        TextView cameraArea = findViewById(R.id.camera_bt);
        TextView photoArea = findViewById(R.id.photo_bt);

        cameraArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查是否已经获得相机的权限
                /*if (verifyPermissions(MainActivity.this, PERMISSIONS_STORAGE[2]) == 0) {
                    Log.i(TAG, "提示是否要授权");
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, 3);
                } else {
                    //已经有权限
                    toCamera();  //打开相机
                }*/
            }
        });
        photoArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*toPicture();*/
            }
        });
    }

    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this, "相册选择返回", Toast.LENGTH_LONG).show();
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));


                        String photoDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/breast/symmetry/Evaluation/";
                        System.out.println(photoDir);
                        //获取内部存储状态
                        String state = Environment.getExternalStorageState();
                        //如果状态不是mounted，无法读写
                        if (!state.equals(Environment.MEDIA_MOUNTED)) {
                            return;
                        }
                        //通过UUID生成字符串文件名
                        String fileName1 = UUID.randomUUID().toString();
                        try {
                            File file = new File(photoDir + fileName1 + ".jpg");
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                            //保存图片后发送广播通知更新数据库
                            Uri uri = Uri.fromFile(file);
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                            Toast.makeText(this, "拍照结果保存成功", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //camereIv.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case TAKE_CAMARA:
                if (resultCode == RESULT_OK) {
                    try {
                        //将相册的照片显示出来
                        Uri uri_photo = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri_photo));
                        //photoIv.setImageBitmap(bitmap);

                        String photoDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/breast/symmetry/Evaluation/";
                        System.out.println(photoDir);
                        //获取内部存储状态
                        String state = Environment.getExternalStorageState();
                        //如果状态不是mounted，无法读写
                        if (!state.equals(Environment.MEDIA_MOUNTED)) {
                            System.out.println("无法存储");
                            return;
                        }
                        //通过UUID生成字符串文件名
                        String fileName1 = UUID.randomUUID().toString();
                        try {
                            File file = new File(photoDir + fileName1 + ".jpg");
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                            //保存图片后发送广播通知更新数据库
                            Uri uri = Uri.fromFile(file);
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                            Toast.makeText(this, "拍照结果保存成功", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

    }


    /**
     * 检查是否有对应权限
     *
     * @param activity   上下文
     * @param permission 要检查的权限
     * @return 结果标识
     */
    public int verifyPermissions(Activity activity, java.lang.String permission) {
        int Permission = ActivityCompat.checkSelfPermission(activity, permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "已经同意权限");
            return 1;
        } else {
            Log.i(TAG, "没有同意权限");
            return 0;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "用户授权");
            //toCamera();
        } else {
            Log.i(TAG, "用户未授权");
        }
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent, TAKE_CAMARA);
        Log.i(TAG, "跳转相册成功");
    }

    //跳转相机
    private void toCamera() {
        //创建File对象，用于存储拍照后的图片
//        File outputImage = new File(getExternalCacheDir(), "outputImage.jpg");
        File outputImage = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (outputImage.exists()) {
            outputImage.delete();
        } else {
            try {
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断SDK版本高低，ImageUri方法不同
        if (Build.VERSION.SDK_INT >= 24) {
            ImageUri = FileProvider.getUriForFile(MainActivity.this, "com.BreastSymmetryEvaluation.fileprovider", outputImage);
        } else {
            ImageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }
}
