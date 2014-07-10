package com.blue.sky.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.blue.sky.component.R;

public class MainTestFragment extends Fragment {

    private Context context;
	public MainTestFragment(Context context){
        this.context = context;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_main_test_fragment, container, false);

        TextView tab = (TextView)rootView.findViewById(R.id.tab_fragment);
        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),com.blue.sky.tab.tabfragment.MainActivity.class);
                startActivity(intent);
            }
        });

        TextView navigation = (TextView)rootView.findViewById(R.id.tab_navigation);
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),com.blue.sky.tab.navigation.MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
