package ru.saoworld.sm.swortmasters.bin.custom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import ru.saoworld.sm.swortmasters.bin.chat.Chat;
import ru.saoworld.sm.swortmasters.bin.GameMainWindow;
import ru.saoworld.sm.swortmasters.bin.hero.HeroMainWindow;


public class NavigationEvents  extends AppCompatActivity{
    public void chatButtonRequest(View v)
    {
        finish();
        Intent intent = new Intent(this,Chat.class);
        startActivity(intent);
    }
    public void heroButtonRequest(View v)
    {
        finish();
        Intent intent = new Intent(this,HeroMainWindow.class);
        startActivity(intent);
    }
    public void messageButtonRequest(View v)
    {
        Toast.makeText(getApplicationContext(),"Message",Toast.LENGTH_SHORT).show();
    }
    public void mainButtonRequest(View v)
    {
        finish();
        Intent intent = new Intent(this,GameMainWindow.class);
        startActivity(intent);
    }

}
