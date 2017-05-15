package ru.saoworld.sm.swortmasters.dao.impl.user.impl;

import android.content.Entity;

import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.saoworld.sm.swortmasters.bin.bean.Characteristics;
import ru.saoworld.sm.swortmasters.bin.bean.GameItem;
import ru.saoworld.sm.swortmasters.bin.chat.ChatMessage;
import ru.saoworld.sm.swortmasters.bin.hero.config.HeroInventoryAdapter;
import ru.saoworld.sm.swortmasters.dao.AbstractDAOController;
import ru.saoworld.sm.swortmasters.dao.impl.user.IUserController;

public class UserDAO extends AbstractDAOController<Characteristics, Integer> implements IUserController {

    public Characteristics getCharacketristics(int id) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getCharacterStats.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj = JSONValue.parse(answear);
        org.json.simple.JSONArray array = ((org.json.simple.JSONArray) answearObj);
        org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) array.get(0);
        Characteristics characteristics = new Characteristics();
        characteristics.setName(String.valueOf(obj2.get("sm_user_login")));
        characteristics.setAgility(Integer.valueOf(String.valueOf(obj2.get("sm_user_agility"))));
        characteristics.setStamina(Integer.valueOf(String.valueOf(obj2.get("sm_user_stamina"))));
        characteristics.setStrength(Integer.valueOf(String.valueOf(obj2.get("sm_user_strength"))));
        characteristics.setFreeChar(Integer.valueOf(String.valueOf(obj2.get("sm_user_free_char"))));
        characteristics.setHp(Integer.valueOf(String.valueOf(obj2.get("sm_user_hp"))));
        characteristics.setCopper(Integer.valueOf(String.valueOf(obj2.get("sm_user_copper"))));
        return characteristics;
    }


    public boolean addStaminaPoint(int id) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/addStaminaPoint.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body().string().equals("ok"))
            return true;
        else
            return false;
    }

    public boolean addAgilityPoint(int id) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/addAgilityPoint.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body().string().equals("ok"))
            return true;
        else
            return false;
    }

    public boolean addStrengthPoint(int id) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/addStrengthPoint.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body().string().equals("ok"))
            return true;
        else
            return false;
    }

    public boolean healUser(int userId) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("userId", String.valueOf(userId));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/healUser.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String temp = response.body().string();
        if (temp.equals("ok"))
            return true;
        else
            return false;
    }

    public boolean changeUserLocation(int userId, int cityId) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("cityId", String.valueOf(cityId));
        data.put("userId", String.valueOf(userId));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/changeUserLocation.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String temp = response.body().string();
        if (temp.equals("ok"))
            return true;
        else
            return false;
    }

    public boolean changeUserSavePoint(int userId, int cityId) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("cityId", String.valueOf(cityId));
        data.put("userId", String.valueOf(userId));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/changeUserSavePoint.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String temp = response.body().string();
        if (temp.equals("ok"))
            return true;
        else
            return false;
    }

    public void getUserInventory(int id, HeroInventoryAdapter adp) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("userId", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getUserItems.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj = JSONValue.parse(answear);
        if (answearObj != null) {
            org.json.simple.JSONArray array = ((org.json.simple.JSONArray) answearObj);
            for (int i = 0; i < ((org.json.simple.JSONArray) answearObj).size(); i++) {
                org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) array.get(i);

                GameItem gameItem = new GameItem(Integer.valueOf(String.valueOf(obj2.get("sm_item_id"))), (String) obj2.get("sm_item_name"), (String) obj2.get("sm_item_description")
                        , Integer.valueOf(String.valueOf(obj2.get("sm_item_stamina"))), Integer.valueOf(String.valueOf(obj2.get("sm_item_agility"))),
                        Integer.valueOf(String.valueOf(obj2.get("sm_item_strength"))), Integer.valueOf(String.valueOf(obj2.get("sm_item_attack"))),
                        Integer.valueOf(String.valueOf(obj2.get("sm_item_health"))), Integer.valueOf(String.valueOf(obj2.get("sm_item_dodge"))),
                        Integer.valueOf(String.valueOf(obj2.get("sm_item_defense"))), Integer.valueOf(String.valueOf(obj2.get("sm_item_speed"))),
                        Integer.valueOf(String.valueOf(obj2.get("sm_item_level"))),Integer.valueOf(String.valueOf(obj2.get("sm_item_cost"))));
                adp.add(gameItem);
            }
        }
    }

    public boolean deleteItemFromUser(int userId, int itemId) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("itemId", String.valueOf(itemId));
        data.put("userId", String.valueOf(userId));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/deleteItemFromUser.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String temp = response.body().string();
        if (temp.equals("ok"))
            return true;
        else
            return false;
    }

    public Characteristics getRandomEnemy(int id) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getEnemy.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj = JSONValue.parse(answear);
        Characteristics characteristics = new Characteristics();
        if (answearObj != null) {
            org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) answearObj;
            /*org.json.simple.JSONArray array = ((org.json.simple.JSONArray) answearObj);
            org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) array.get(0);*/
            characteristics.setName(String.valueOf(obj2.get("sm_user_login")));
            characteristics.setAgility(Integer.valueOf(String.valueOf(obj2.get("sm_user_agility"))));
            characteristics.setStamina(Integer.valueOf(String.valueOf(obj2.get("sm_user_stamina"))));
            characteristics.setStrength(Integer.valueOf(String.valueOf(obj2.get("sm_user_strength"))));
            characteristics.setId(Integer.valueOf(String.valueOf(obj2.get("sm_user_id"))));
        }
        return characteristics;
    }

    public String newFight(int id, int id1) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("userId", String.valueOf(id));
        data.put("enemyId", String.valueOf(id1));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/fightEnemy.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String temp = response.body().string();
        Characteristics characteristics = getCharacketristics(id);
        if(characteristics.getHp()==0)
        {
            changeUserLocation(id,getUserSavePoint(id));
        }
        return temp;
    }
    public String getUserTotalStats(int id)throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getTotalStats.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        return answear;
    }
    private int getUserSavePoint(int id) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getCharacterSavePoint.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj = JSONValue.parse(answear);
        org.json.simple.JSONArray array = ((org.json.simple.JSONArray) answearObj);
        org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) array.get(0);
        int savePointId=(Integer.valueOf(String.valueOf(obj2.get("sm_user_save_point"))));
        return savePointId;
    }

    public boolean getIfUserCanFight(int id) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getCharacterStats.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj = JSONValue.parse(answear);
        org.json.simple.JSONArray array = ((org.json.simple.JSONArray) answearObj);
        org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) array.get(0);
        int userHp = Integer.valueOf(String.valueOf(obj2.get("sm_user_hp")));
        if (userHp > 0)
            return true;
        else return false;
    }
}
