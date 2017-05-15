package ru.saoworld.sm.swortmasters.bin.subsidiary.teleport;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.custom.NavigationEvents;
import ru.saoworld.sm.swortmasters.bin.subsidiary.teleport.config.CityTeleportAdapter;
import ru.saoworld.sm.swortmasters.dao.impl.location.impl.LocationDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

public class TeleportWindow extends NavigationEvents {

    private CityTeleportAdapter adp;
    LocationDAO locationDAO;
    private ListView list;
    String MyPREFERENCES = "Sword Masters";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleport_window);
        list = (ListView) findViewById(R.id.cityList);
        locationDAO = new LocationDAO();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        adp =  new CityTeleportAdapter(sharedpreferences.getInt("Id",0),getApplicationContext(), R.layout.single_city);;
        try {
            locationDAO.getCityList(adp);
        } catch (IOException e) {
            new DataException(e);
        }
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            public void OnChanged(){
                super.onChanged();
                list.setSelection(adp.getCount() -1);
            }
        });

    }
}
