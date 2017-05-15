package ru.saoworld.sm.swortmasters.bin.subsidiary.duel;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.bean.Characteristics;
import ru.saoworld.sm.swortmasters.bin.custom.NavigationEvents;
import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

public class DuelWindow extends NavigationEvents {
    TextView staminaText;
    TextView strengthText;
    TextView agilityText;
    TextView heroName;
    TextView resulteText;
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
        setContentView(R.layout.activity_duel_window);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        id = sharedpreferences.getInt("Id", 0);
        staminaText = (TextView) findViewById(R.id.staminaText);
        strengthText = (TextView) findViewById(R.id.strengthText);
        totalStats = (TextView) findViewById(R.id.totalStats);
        agilityText = (TextView) findViewById(R.id.agilityText);
        userAvatar = (ImageView) findViewById(R.id.userAvatar);
        heroName = (TextView) findViewById(R.id.heroName);
        resulteText = (TextView) findViewById(R.id.battleResult);
        userDAO = new UserDAO();
        try {
            updateData();
        } catch (IOException e) {
            new DataException(e);
        }
    }

    public void findRequest(View v) throws IOException {
        updateData();
    }

    public void fightRequest(View v) throws IOException {
        if (userDAO.getIfUserCanFight(id)) {
            battle(this.id, characteristics.getId());
            updateData();
        }
        else
            Toast.makeText(getApplicationContext(), "You need to reset your hp! ", Toast.LENGTH_LONG).show();

    }

    private void battle(int id, int id1) throws IOException {
        String result = userDAO.newFight(id, id1);
        resulteText.setText(result);

    }

    private void updateData() throws IOException {
        characteristics = userDAO.getRandomEnemy(id);
        heroName.setText(characteristics.getName());
        staminaText.setText("Stamina: " + Integer.toString(characteristics.getStamina()));
        strengthText.setText("Strength: " + Integer.toString(characteristics.getStrength()));
        agilityText.setText("Agility: " + Integer.toString(characteristics.getAgility()));
        totalStats.setText("Total: "+userDAO.getUserTotalStats(characteristics.getId()));
        userAvatar.setImageResource(R.drawable.kirito);
    }
}
