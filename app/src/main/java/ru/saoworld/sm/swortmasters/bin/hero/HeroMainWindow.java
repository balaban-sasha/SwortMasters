package ru.saoworld.sm.swortmasters.bin.hero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.bean.Characteristics;
import ru.saoworld.sm.swortmasters.bin.custom.NavigationEvents;
import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

public class HeroMainWindow extends NavigationEvents {

    TextView staminaText;
    TextView strengthText;
    TextView agilityText;
    TextView numOfCharacketeristics;
    TextView heroName;
    TextView hpText;
    TextView copperText;
    TextView totalStats;
    Characteristics characteristics;
    UserDAO userDAO;
    ImageView userAvatar;
    String MyPREFERENCES = "Sword Masters";
    SharedPreferences sharedpreferences;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_main_window);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        id = sharedpreferences.getInt("Id", 0);
        staminaText = (TextView) findViewById(R.id.staminaText);
        strengthText = (TextView) findViewById(R.id.strengthText);
        agilityText = (TextView) findViewById(R.id.agilityText);
        userAvatar = (ImageView) findViewById(R.id.userAvatar);
        totalStats = (TextView) findViewById(R.id.totalStats);
        heroName = (TextView)findViewById(R.id.heroName);
        hpText = (TextView)findViewById(R.id.hpText);
        copperText = (TextView)findViewById(R.id.copperText);
        numOfCharacketeristics = (TextView) findViewById(R.id.numOfCharacketeristics);
        userDAO = new UserDAO();
        try {
            updateData();
        } catch (IOException e) {
            new DataException(e);
        }
    }
    public void heroInventoryRequest(View v) {
        finish();
        Intent intent = new Intent(HeroMainWindow.this, HeroInventory.class);
        startActivity(intent);
    }
    private void updateData() throws IOException {
        characteristics = userDAO.getCharacketristics(id);
        heroName.setText(characteristics.getName());
        numOfCharacketeristics.setText("Free: " + Integer.toString(characteristics.getFreeChar()));
        staminaText.setText("Stamina: " + Integer.toString(characteristics.getStamina()));
        strengthText.setText("Strength: " + Integer.toString(characteristics.getStrength()));
        agilityText.setText("Agility: " + Integer.toString(characteristics.getAgility()));
        hpText.setText("Hp: " + Integer.toString(characteristics.getHp()));
        copperText.setText("Copper: " + Integer.toString(characteristics.getCopper()));
        totalStats.setText("Total: " + userDAO.getUserTotalStats(id));
        userAvatar.setImageResource(R.drawable.kirito);
    }

    private boolean checkIfFreeExist() {
        if (characteristics.getFreeChar()>0)
            return true;
        else return false;
    }

    public void addStaminaPoint(View v) throws IOException {
        if((checkIfFreeExist())&&(userDAO.addStaminaPoint(this.id)))
        {
            Toast.makeText(getApplicationContext(), "Stamina point were add!", Toast.LENGTH_SHORT).show();
            updateData();
        }
    }

    public void addStrengthPoint(View v) throws IOException {
        if((checkIfFreeExist())&&(userDAO.addStrengthPoint(this.id)))
        {
            Toast.makeText(getApplicationContext(), "Strength point were add!", Toast.LENGTH_SHORT).show();
            updateData();
        }
    }

    public void addAgilityPoint(View v) throws IOException {
        if((checkIfFreeExist())&&(userDAO.addAgilityPoint(this.id)))
        {
            Toast.makeText(getApplicationContext(), "Agility point were add!", Toast.LENGTH_SHORT).show();
            updateData();
        }
    }
}
