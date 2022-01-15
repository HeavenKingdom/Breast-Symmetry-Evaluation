package com.example.breast_symmetry_evaluation.Fragment;

import static com.example.breast_symmetry_evaluation.MainActivity.TAKE_CAMARA;
import static com.example.breast_symmetry_evaluation.MainActivity.TAKE_PHOTO;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breast_symmetry_evaluation.Camera.CameraActivity;
import com.example.breast_symmetry_evaluation.MainActivity;
import com.example.breast_symmetry_evaluation.R;

import com.example.breast_symmetry_evaluation.Adapter.HelpAdapter;
import com.example.breast_symmetry_evaluation.Screen.ImagePreviewScreen;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_OK = -1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Uri imageUri;

    private String[] groups = {"给患者拍照时有什么注意事项", "如何注册账号", "遇到问题如何反馈", "各种问题帮助..."};

    private String[][] childs = {{"将患者的胸部对准在圆形区域内，保持设备稳定后即可拍照"},
            {"认证的医生登录自己的工牌号绑定手机后即可设置密码注册"},
            {"发送到xxxxx@qq.com邮箱"},
            {"可以折叠/展开的内容区域...................................................."}};

    public StartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance(String param1, String param2) {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPhoto();
        initHelpList();
    }

    @SuppressLint("SdCardPath")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), "相册选择返回", Toast.LENGTH_LONG).show();
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    /*try {
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
                    }*/
                }
                break;
            case 100:
                System.out.println("相册选择");
                if (resultCode == RESULT_OK) {
                    System.out.println("相册选择");
                    try {
                        //将相册的照片显示出来
                        Uri uri_photo = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri_photo));

                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        byte[] imageFile=outputStream.toByteArray();

                        Intent intent=new Intent(getContext(), ImagePreviewScreen.class);
                        intent.putExtra("image",imageFile);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }



    /*
     * 初始化帮助列表
     * */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void initHelpList() {
        ExpandableListView helpList = (ExpandableListView) getActivity().findViewById(R.id.help_list);
        //helpList.setGroupIndicator(getActivity().getResources().getDrawable(R.drawable.main_help_indicator));
        HelpAdapter helpAdapter = new HelpAdapter(getActivity(), groups, childs);
        helpList.setAdapter(helpAdapter);
    }

    /*
     * 初始化图片上传功能
     * */
    private void initPhoto() {
        TextView cameraArea = (TextView) getActivity().findViewById(R.id.camera_bt);
        TextView photoArea = (TextView) getActivity().findViewById(R.id.photo_bt);

        cameraArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*System.out.println("点击拍照");*/

                Intent intent=new Intent(getActivity(), CameraActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        photoArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*toPicture();*/
                System.out.println("相册");
                Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,100);

                Log.i("TAG", "跳转相册成功");
            }
        });
    }
}