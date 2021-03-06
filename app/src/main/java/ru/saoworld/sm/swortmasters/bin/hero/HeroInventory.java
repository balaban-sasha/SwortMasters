package ru.saoworld.sm.swortmasters.bin.hero;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.custom.NavigationEvents;
import ru.saoworld.sm.swortmasters.bin.hero.config.HeroInventoryAdapter;
import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

public class HeroInventory extends NavigationEvents {
    private HeroInventoryAdapter adp;
    UserDAO userDAO;
    private ListView list;
    String MyPREFERENCES = "Sword Masters";
    SharedPreferences sharedpreferences;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_inventory);
        list = (ListView) findViewById(R.id.itemList);
        userDAO = new UserDAO();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        adp = new HeroInventoryAdapter(sharedpreferences.getInt("Id", 0), getApplicationContext(), R.layout.single_inventory_item);
        try {
            userDAO.getUserInventory(sharedpreferences.getInt("Id", 0), adp);
        } catch (IOException e) {
            new DataException(e);
        }
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            public void OnChanged() {
                try {
                    userDAO.getUserInventory(sharedpreferences.getInt("Id", 0), adp);
                } catch (IOException e) {
                    new DataException(e);
                }
                super.onChanged();
                list.setSelection(adp.getCount() - 1);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("HeroInventory Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
