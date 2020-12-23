package com.coursework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.coursework.DataManager.filtersCities;
import static com.coursework.DataManager.filtersDate;

public class filters extends Activity {

    DatePickers dtPrev;
    DatePickers dtPost;



    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filtersCities.clear();
        filtersDate.clear();
        setContentView(R.layout.activity_filters);
        final ExpandableListView listView = (ExpandableListView)findViewById(R.id.exListView);

        ArrayList <String>cities=DataManager.selectAllAddresses(getApplicationContext());
        Set<String> cloud= new LinkedHashSet<>(cities);

        cities.clear();
        cities.addAll(cloud);

        ArrayList<ArrayList<String>> city=new ArrayList<>();
        city.add(cities);

        final ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), city);
        listView.setAdapter(adapter);

        final TextView prevDate=findViewById(R.id.prevDate);
        final TextView postDate=findViewById(R.id.postDate);
        final Button accept=findViewById(R.id.accept);

        prevDate.setText(DataManager.minDate(getApplicationContext()));
        postDate.setText(DataManager.maxDate(getApplicationContext()));
        dtPrev=new DatePickers(getApplicationContext(),prevDate);
        dtPost=new DatePickers(getApplicationContext(),postDate);


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                CheckBox checkBox = v.findViewById(R.id.checkboxChild);
                TextView textView=v.findViewById(R.id.textChild);

                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    if(filtersCities.contains(textView.getText().toString()));
                    {
                        filtersCities.remove(textView.getText().toString());
                    }

                } else {
                    checkBox.setChecked(true);
                    filtersCities.add(textView.getText().toString());

                }
                return true;
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtersDate.add(prevDate.getText().toString());
                filtersDate.add(postDate.getText().toString());
                if(filtersCities.size()>0 && filtersDate.size()>0)
                {

                    DataManager.CUSTOM_QUERY_WHERE="select "+dbHelper.KEY_ID+" from "+dbHelper.TABLE_violations+" WHERE";
                    DataManager.CUSTOM_QUERY_WHERE_FOR_MAP="select "+dbHelper.KEY_VIOLATIONGPS+" from "+dbHelper.TABLE_violations+" WHERE";
                    for(int i = 0; i< filtersCities.size(); i++) {
                        if(i==0)
                        DataManager.CUSTOM_QUERY_WHERE += " gps like '" + filtersCities.get(i)+"%'";
                        DataManager.CUSTOM_QUERY_WHERE_FOR_MAP+= " gps like '" + filtersCities.get(i)+"%'";
                        if(i>0)
                            DataManager.CUSTOM_QUERY_WHERE += " or gps like '" + filtersCities.get(i)+"%'";
                        DataManager.CUSTOM_QUERY_WHERE_FOR_MAP += " or gps like '" + filtersCities.get(i)+"%'";
                    }
                    DataManager.CUSTOM_QUERY_WHERE +=" and Date between '"+filtersDate.get(0)+"' and '"+filtersDate.get(1)+"'";
                            DataManager.CUSTOM_QUERY_WHERE_FOR_MAP+=" and Date between '"+filtersDate.get(0)+"' and '"+filtersDate.get(1)+"'";
                }
                if(filtersCities.size()<1 && filtersDate.size()>0)
                {
                    DataManager.CUSTOM_QUERY_WHERE="select "+dbHelper.KEY_ID+" from "+dbHelper.TABLE_violations+" WHERE  Date Between ";
                    DataManager.CUSTOM_QUERY_WHERE_FOR_MAP="select "+dbHelper.KEY_VIOLATIONGPS+" from "+dbHelper.TABLE_violations+" WHERE  Date Between ";
                            DataManager.CUSTOM_QUERY_WHERE +="'"+filtersDate.get(0)+"' and '"+filtersDate.get(1)+"'";
                    DataManager.CUSTOM_QUERY_WHERE_FOR_MAP+="'"+filtersDate.get(0)+"' and '"+filtersDate.get(1)+"'";
                    }


                startActivity(new Intent(filters.this,HistoryOfViolations.class));
            }
        });

    }

    public void openDateOrTimeInspector(View view) {
        switch (view.getId()) {
            case R.id.prevDate:
                dtPrev.setDate(this);
                break;
            case R.id.postDate:
                dtPost.setDate(this);
                break;
        }
    }

}
