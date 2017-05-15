package ru.saoworld.sm.swortmasters.dao.impl.shop.impl;

import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.saoworld.sm.swortmasters.bin.bean.GameItem;
import ru.saoworld.sm.swortmasters.bin.chat.ChatMessage;
import ru.saoworld.sm.swortmasters.bin.subsidiary.shop.config.ShopAdapter;
import ru.saoworld.sm.swortmasters.dao.AbstractDAOController;
import ru.saoworld.sm.swortmasters.dao.impl.shop.IShopController;

public class ShopDAO extends AbstractDAOController<GameItem,Integer> implements IShopController {
    @Override
    public void getShopItem(ShopAdapter adp, int shopId) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("shopId", String.valueOf(shopId));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getShopItems.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj= JSONValue.parse(answear);
        org.json.simple.JSONArray array=((org.json.simple.JSONArray)answearObj);
        for (int i=0;i< ((org.json.simple.JSONArray) answearObj).size();i++)
        {
            org.json.simple.JSONObject obj2=(org.json.simple.JSONObject)array.get(i);

            GameItem gameItem = new GameItem(Integer.valueOf(String.valueOf(obj2.get("sm_item_id"))),(String)obj2.get("sm_item_name"),(String)obj2.get("sm_item_description")
                    ,Integer.valueOf(String.valueOf(obj2.get("sm_item_stamina"))),Integer.valueOf(String.valueOf(obj2.get("sm_item_agility"))),
                    Integer.valueOf(String.valueOf(obj2.get("sm_item_strength"))),Integer.valueOf(String.valueOf(obj2.get("sm_item_attack"))),
                    Integer.valueOf(String.valueOf(obj2.get("sm_item_health"))),Integer.valueOf(String.valueOf(obj2.get("sm_item_dodge"))),
                    Integer.valueOf(String.valueOf(obj2.get("sm_item_defense"))),Integer.valueOf(String.valueOf(obj2.get("sm_item_speed"))),
                    Integer.valueOf(String.valueOf(obj2.get("sm_item_level"))),Integer.valueOf(String.valueOf(obj2.get("sm_item_cost"))));
            adp.add(gameItem);
        }

    }

    public boolean addItemToUser(int userId,int itemId) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("itemId", String.valueOf(itemId));
        data.put("userId", String.valueOf(userId));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/addItemToUser.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String temp=response.body().string();
        if (temp.equals("ok"))
            return true;
        else
            return false;
    }
}
