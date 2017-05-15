package ru.saoworld.sm.swortmasters.bin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.bean.City;
import ru.saoworld.sm.swortmasters.bin.custom.NavigationEvents;
import ru.saoworld.sm.swortmasters.bin.subsidiary.duel.DuelWindow;
import ru.saoworld.sm.swortmasters.bin.subsidiary.shop.ShopWindow;
import ru.saoworld.sm.swortmasters.bin.subsidiary.teleport.TeleportWindow;
import ru.saoworld.sm.swortmasters.dao.impl.location.impl.LocationDAO;
import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameMainWindow extends NavigationEvents {

    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Sword Masters";
    LocationDAO locationDAO;
    UserDAO userDAO;
    City city;
    int id;
    TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main_window);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        id = sharedpreferences.getInt("Id", 0);
        locationDAO = new LocationDAO();
        try {
            city = locationDAO.getCharacterCity(id);
        } catch (IOException e) {
            new DataException(e);
        }
        cityName = (TextView) findViewById(R.id.cityName);
        cityName.setText(city.getCityName());
        userDAO = new UserDAO();
    }

    public void savePointRequest(View v) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Save point");
        dlgAlert.setMessage("Are you sure that want to save your hero in this city?");
        dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (userDAO.changeUserSavePoint(id, city.getCityId())) {
                        Toast.makeText(getApplicationContext(), "All is done!", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    new DataException(e);
                }
            }
        });
        dlgAlert.setNegativeButton("No", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

    }

    public void tpRequest(View v) {
        finish();
        Intent intent = new Intent(GameMainWindow.this, TeleportWindow.class);
        startActivity(intent);
    }

    public void duelRequest(View v) {
        finish();
        Intent intent = new Intent(GameMainWindow.this, DuelWindow.class);
        startActivity(intent);
    }

    public void churchRequest(View v) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Church");
        dlgAlert.setMessage("Are you sure that want to pay 10 copper for hp restore?");
        dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (userDAO.healUser(id))
                                Toast.makeText(getApplicationContext(), "All is done, your hp is full! ", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Something wrong. May be you don't have enough copper!", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            new DataException(e);
                        }
                    }
                }
        );
        dlgAlert.setNegativeButton("No", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().

                show();
    }

    public void shopRequest(View v) {
        finish();
        Intent intent = new Intent(GameMainWindow.this, ShopWindow.class);
        startActivity(intent);
    }


}
