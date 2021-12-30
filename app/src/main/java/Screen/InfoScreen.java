package Screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.breast_symmetry_evaluation.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InfoScreen extends AppCompatActivity {
    private List<Integer> list=new ArrayList<Integer>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_info_screen);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //对每个标签项设置选中监听
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getId();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i=0;i<10;i++){
            Integer integer=Integer.valueOf(i);
            list.add(integer);
            System.out.println("添加第"+i+"个数据");
        }

        ListView listView=findViewById(R.id.list_view);

        InfoAdapter infoAdapter=new InfoAdapter(getApplicationContext(),list);
        listView.setAdapter(infoAdapter);

    }
    public void call(View view) {
        TextView userPhone=findViewById(R.id.user_phone);
        String phoneNumber=(String) userPhone.getText();
        Log.i("电话号码为",phoneNumber);
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    /*public View.OnClickListener call() {
        TextView userPhone=findViewById(R.id.user_phone);
        String phoneNumber=(String) userPhone.getText();
        Log.i("电话号码为",phoneNumber);
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
        return null;
    }*/
}