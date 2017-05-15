package ru.saoworld.sm.swortmasters.dao.impl.chat.impl;

import android.content.Entity;
import android.content.Intent;
import android.view.ViewDebug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.saoworld.sm.swortmasters.bin.chat.ChatMessage;
import ru.saoworld.sm.swortmasters.bin.chat.ChatMessageAdapter;
import ru.saoworld.sm.swortmasters.dao.AbstractDAOController;
import ru.saoworld.sm.swortmasters.dao.impl.chat.IChatController;

public class ChatDAO extends AbstractDAOController<Entity,Integer> implements IChatController {

    private int idOfLastMessage=0;
    private void setLastMessageId(int id)
    {
        this.idOfLastMessage= id;
    }
    private int getLastMessageId()
    {
        return this.idOfLastMessage;
    }
    public ChatMessageAdapter getMessages(ChatMessageAdapter adp) throws IOException, JSONException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("lastMessageId", String.valueOf(this.idOfLastMessage));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getChatMessage.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj= JSONValue.parse(answear);
        org.json.simple.JSONArray array=((org.json.simple.JSONArray)answearObj);
        for (int i=0;i< ((org.json.simple.JSONArray) answearObj).size();i++)
        {
            org.json.simple.JSONObject obj2=(org.json.simple.JSONObject)array.get(i);
            adp.add(new ChatMessage(false, (String)obj2.get("sm_chat_user_name"),(String)obj2.get("sm_chat_message")));
            if(((org.json.simple.JSONArray) answearObj).size()==(i+1))
                setLastMessageId((Integer)obj2.get("sm_chat_id"));
        }
        return adp;
    }
    private String addNewmessage(int id, String message) throws IOException, JSONException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        data.put("message", message);
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/newChatMessage.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public boolean sendNewMessage(String s, int id) throws IOException, JSONException {
        if(!s.equals("111"))
        {
            addNewmessage(id,s);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void getNewMessageIfExist(ChatMessageAdapter adp) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("lastMessageId", String.valueOf(this.idOfLastMessage));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getChatMessage.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj= JSONValue.parse(answear);
        org.json.simple.JSONArray array=((org.json.simple.JSONArray)answearObj);
        for (int i=0;i< ((org.json.simple.JSONArray) answearObj).size();i++)
        {
            org.json.simple.JSONObject obj2=(org.json.simple.JSONObject)array.get(i);
            adp.add(new ChatMessage(false, (String)obj2.get("sm_chat_user_name"),(String)obj2.get("sm_chat_message")));
            if(((org.json.simple.JSONArray) answearObj).size()==(i+1))
                setLastMessageId((Integer)obj2.get("sm_chat_id"));
        }
    }
}
