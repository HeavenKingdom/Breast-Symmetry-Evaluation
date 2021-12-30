package Screen;

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

public class InfoAdapter extends BaseAdapter {

    private Context context=null;
    private List<Integer> list=null;

    public InfoAdapter(Context context, List<Integer> list) {
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
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("点击了",getItemId(i)+"个item");
            }
        });
        ImageView userAvatar=item.findViewById(R.id.user_avatar);
        userAvatar.setImageResource(R.drawable.avatar);

        TextView userName=item.findViewById(R.id.user_name);
        userName.setText("花生");

        TextView userPhone=item.findViewById(R.id.user_phone);
        userPhone.setText("15333442211");

        ImageView phoneIcon=item.findViewById(R.id.phone_icon);
        phoneIcon.setImageResource(R.drawable.phone);
        InfoScreen infoScreen=new InfoScreen();
        //phoneIcon.setOnClickListener(infoScreen.call());

        TextView medicalTime=item.findViewById(R.id.medical_time);
        medicalTime.setText("今天 14:00");

        TextView seeDetails=item.findViewById(R.id.see_details);
        seeDetails.setText("查看详情");



        return item;
    }
}

