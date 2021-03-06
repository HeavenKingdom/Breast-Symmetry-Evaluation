package com.example.breast_symmetry_evaluation.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breast_symmetry_evaluation.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.breast_symmetry_evaluation.Adapter.InfoAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment implements AdapterView.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        return inflater.inflate(R.layout.activity_info_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData(){

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        //????????????????????????????????????
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("???????????????tab???" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //???????????????
        for (int i = 0; i < 10; i++) {
            Map<String, String> name = new HashMap<>();
            name.put("id", i + "");
            name.put("userName", "??????");
            name.put("userPhone", "15333442211");
            name.put("medicalTime", "??????14:00");
            if (i % 2 == 0) {
                name.put("visibility", "0");
            } else {
                name.put("visibility", "8");
            }
            list.add(name);
            System.out.println("?????????" + i + "?????????");
        }

        ListView listView = getActivity().findViewById(R.id.list_view);
        //???????????????
        InfoAdapter infoAdapter = new InfoAdapter(getActivity().getApplicationContext(), list);
        listView.setAdapter(infoAdapter);

        listView.setOnItemClickListener(this);

    }

    /*
     * ???listView??????????????????????????????
     * */

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView userPhone = (TextView) view.findViewById(R.id.user_phone);
        Log.i("?????????", (String) userPhone.getText());
        System.out.println((String) userPhone.getText());
        String showText = "?????????" + i + "????????????????????????" + userPhone.getText() + "???ID??????" + l;
        Toast.makeText(getActivity(), showText, Toast.LENGTH_LONG).show();
        String phoneNumber = (String) userPhone.getText();
        Log.i("???????????????", phoneNumber);

    }
}