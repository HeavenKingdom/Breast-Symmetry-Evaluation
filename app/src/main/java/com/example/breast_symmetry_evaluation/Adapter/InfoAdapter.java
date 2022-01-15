package com.example.breast_symmetry_evaluation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.breast_symmetry_evaluation.MainActivity;
import com.example.breast_symmetry_evaluation.R;

import java.util.List;
import java.util.Map;

public class InfoAdapter extends BaseAdapter {

    private Context context=null;
    private List<Map<String,String>> list=null;

    public InfoAdapter(Context context, List<Map<String,String>> list) {
        this.context = context;
        this.list = list;
        Log.i("list长度：",this.list.size()+"");
        Log.i("上下文：",this.context+"");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item = null;
        //布局不变，数据变

        //如果缓存为空，我们生成新的布局作为1个item
        if(view==null){
            Log.i("info:", "没有缓存，重新生成"+i);
            LayoutInflater inflater=LayoutInflater.from(context);
            //因为getView()返回的对象，adapter会自动赋给ListView
            item = inflater.inflate(R.layout.list_item_layout, null);
        }else{
            Log.i("info:", "有缓存，不需要重新生成"+i);
            item = view;
        }
        //动态设置显隐性
        /*if(Integer.parseInt(list.get(i).get("visibility"))==8){
            item.setVisibility(View.GONE);
        }
        if(Integer.parseInt(list.get(i).get("visibility"))==0){
            item.setVisibility(View.VISIBLE);
        }*/
        //头像
        ImageView userAvatar=item.findViewById(R.id.user_avatar);
        userAvatar.setImageResource(R.drawable.avatar);
        //用户名
        TextView userName=item.findViewById(R.id.user_name);
        userName.setText(list.get(i).get("userName"));
        //电话号码
        TextView userPhone=item.findViewById(R.id.user_phone);
        userPhone.setText(list.get(i).get("userPhone"));
        //拨打电话
        ImageView phoneIcon=item.findViewById(R.id.phone_icon);
        phoneIcon.setImageResource(R.drawable.phone);
        phoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber=(String) userPhone.getText();
                Log.i("电话号码为",phoneNumber);

                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));

                context.startActivity(intent);
            }
        });
        //就诊事件
        TextView medicalTime=item.findViewById(R.id.medical_time);
        medicalTime.setText(list.get(i).get("medicalTime"));
        //页面跳转
        TextView seeDetails=item.findViewById(R.id.see_details);
        seeDetails.setText(R.string.detail);
        viewGroup.refreshDrawableState();
        return item;
    }
}

