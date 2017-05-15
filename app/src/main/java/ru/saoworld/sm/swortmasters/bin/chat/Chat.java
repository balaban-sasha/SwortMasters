package ru.saoworld.sm.swortmasters.bin.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.custom.NavigationEvents;
import ru.saoworld.sm.swortmasters.dao.impl.chat.impl.ChatDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

public class Chat extends NavigationEvents{
    private static final String TAG = "ChatActivity";
    private ChatMessageAdapter adp;
    private ListView list;
    private EditText chatText;
    private Button send;
    private boolean side = false;
    private ChatDAO chatDAO;
    String MyPREFERENCES = "Sword Masters";
    SharedPreferences sharedpreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_chat);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        send = (Button) findViewById(R.id.sendMessage);
        list = (ListView) findViewById(R.id.messageList);
        adp = new ChatMessageAdapter(getApplicationContext(), R.layout.single_message);
        list.setAdapter(adp);
        chatText = (EditText) findViewById(R.id.messageText);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode ==
                        KeyEvent.KEYCODE_ENTER)) {
                    try {
                        sendChatMessage();
                    } catch (Exception e) {
                        new DataException(e);
                    }
                }
                return false;
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    sendChatMessage();
                } catch (Exception e) {
                    new DataException(e);
                }
            }
        });
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            public void OnChanged(){
                super.onChanged();
                list.setSelection(adp.getCount() -1);
            }
        });
        chatDAO = new ChatDAO();
        try {
            adp = chatDAO.getMessages(adp);
        } catch (Exception e) {
            new DataException(e);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        try {
                            chatDAO.getNewMessageIfExist(adp);
                        } catch (Exception e) {
                            new DataException(e);
                        }
                    }
                });

            }
        }, 0, 1000);
    }

    private void sendChatMessage() throws IOException, JSONException {
        if(!chatText.getText().toString().equals(""))
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            int id=sharedpreferences.getInt("Id",0);
            if(chatDAO.sendNewMessage(chatText.getText().toString(),id))
            {
                Toast.makeText(getApplicationContext(),"Сообщение успешно отправлено!",Toast.LENGTH_SHORT).show();
                chatText.setText("");
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Сообщение не отправлено!",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Нельзя отправить пустое сообщение",Toast.LENGTH_SHORT).show();
        }
        /*adp.add(new ChatMessage(side, chatText.getText().toString(),"Newbie"));
        chatText.setText("");
        side = !side;*/
    }

}
