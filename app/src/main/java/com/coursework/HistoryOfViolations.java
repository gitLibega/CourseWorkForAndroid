package com.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;


public class HistoryOfViolations extends AppCompatActivity {
int status=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_violations);
        showList();

    }

    /***
     * открыть список выявленных нарушений
     * @param view
     */
    public void showListFrg(View view) {
 showList();

    }
    /***
     * открыть карту, где показаны все напушения
     * @param view
     */
    public void showMapFrg(View view) {
status=2;
        ScrollView lr=(ScrollView) findViewById(R.id.list);
        lr.setVisibility(View.INVISIBLE);
        ScrollView info=(ScrollView) findViewById(R.id.infoAboutMarker);
        info.setVisibility(View.VISIBLE);
        FrameLayout fr=(FrameLayout)findViewById(R.id.mapCon);
        fr.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().add(R.id.mapCon,new MapsFragment()).commit();
    }

    public void openOptions(View view) {
        Intent intent=new Intent(HistoryOfViolations.this,filters.class);
        startActivity(intent);
    }
    public void showList()
    {
        status=1;
        FrameLayout fr=(FrameLayout)findViewById(R.id.mapCon);
        fr.setVisibility(View.INVISIBLE);
        ScrollView info=(ScrollView) findViewById(R.id.infoAboutMarker);
        info.setVisibility(View.INVISIBLE);
        LinearLayout ll= (LinearLayout)findViewById(R.id.frCon);
        ScrollView lr=(ScrollView) findViewById(R.id.list);
        lr.setVisibility(View.VISIBLE);
        if(ll.getChildCount()>0)
            ll.removeAllViews();
        ArrayList<String> idViolations=DataManager.outputViolations(getApplicationContext());

        for (int i = 0; i <idViolations.size() ; i++) {
            itemViolation iv=new itemViolation();
            Bundle bundle = new Bundle();
            bundle.putInt("id",Integer.parseInt(idViolations.get(i)));
            iv.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().add(R.id.frCon,iv).commit();
        }
    }
}