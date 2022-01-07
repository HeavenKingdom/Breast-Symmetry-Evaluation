package Components;

import static com.example.breast_symmetry_evaluation.MainActivity.TAKE_CAMARA;
import static com.example.breast_symmetry_evaluation.MainActivity.TAKE_PHOTO;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.breast_symmetry_evaluation.MainActivity;
import com.example.breast_symmetry_evaluation.R;

import Screen.HelpAdapter;

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
                //检查是否已经获得相机的权限
                /*if (verifyPermissions(MainActivity.this, PERMISSIONS_STORAGE[2]) == 0) {
                    Log.i(TAG, "提示是否要授权");
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, 3);
                } else {
                    //已经有权限
                    toCamera();  //打开相机
                }*/
                System.out.println("点击拍照");

                //申请相机动态权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        photoArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*toPicture();*/
                System.out.println("相册");
                Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
                intent.setType("image/*");
                startActivityForResult(intent, TAKE_CAMARA);
                Log.i("TAG", "跳转相册成功");
            }
        });
    }
}