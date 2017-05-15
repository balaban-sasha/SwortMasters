package ru.saoworld.sm.swortmasters.bin.subsidiary.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.ListView;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.custom.NavigationEvents;
import ru.saoworld.sm.swortmasters.bin.subsidiary.shop.config.ShopAdapter;
import ru.saoworld.sm.swortmasters.dao.impl.location.impl.LocationDAO;
import ru.saoworld.sm.swortmasters.dao.impl.shop.impl.ShopDAO;
import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

public class ShopWindow extends NavigationEvents {

    private ShopAdapter adp;
    ShopDAO shopDAO;
    private ListView list;
    String MyPREFERENCES = "Sword Masters";
    SharedPreferences sharedpreferences;
    LocationDAO locationDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_window);
        locationDAO=new LocationDAO();
        list = (ListView) findViewById(R.id.itemList);
        shopDAO = new ShopDAO();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        int userId = sharedpreferences.getInt("Id", 0);
        adp =  new ShopAdapter(sharedpreferences.getInt("Id",0),getApplicationContext(), R.layout.single_item);;
        try {
            shopDAO.getShopItem(adp,locationDAO.getCharacterCity(userId).getCityId());
        } catch (IOException e) {
            new DataException(e);
        }
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            public void OnChanged(){
                super.onChanged();
                list.setSelection(adp.getCount() -1);
            }
        });
    }

}
