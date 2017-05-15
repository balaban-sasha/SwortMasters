package ru.saoworld.sm.swortmasters.dao.impl.location.impl;

import android.content.Entity;

import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.bean.City;
import ru.saoworld.sm.swortmasters.bin.subsidiary.teleport.config.CityTeleportAdapter;
import ru.saoworld.sm.swortmasters.dao.AbstractDAOController;
import ru.saoworld.sm.swortmasters.dao.impl.location.ILocationController;


public class LocationDAO extends AbstractDAOController<Entity,Integer> implements ILocationController {

    @Override
    public String saveNewLocation() {
        return "All is good!";
    }

    public void getCityList(CityTeleportAdapter adp) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getCityList.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj= JSONValue.parse(answear);
        org.json.simple.JSONArray array=((org.json.simple.JSONArray)answearObj);
        for (int i=0;i< ((org.json.simple.JSONArray) answearObj).size();i++)
        {
            org.json.simple.JSONObject obj2=(org.json.simple.JSONObject)array.get(i);
            City city = new City();
            city.setCityId(Integer.valueOf(String.valueOf(obj2.get("sm_location_id"))));
            city.setCityName(String.valueOf(obj2.get("sm_location_name")));
            adp.add(city);
        }
    }

    public City getCharacterCity(int id) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/getCharacterCity.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = response.body().string();
        Object answearObj = JSONValue.parse(answear);
        org.json.simple.JSONArray array = ((org.json.simple.JSONArray) answearObj);
        org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) array.get(0);
        City city = new City();
        city.setCityId(Integer.valueOf(String.valueOf(obj2.get("sm_location_id"))));
        city.setCityName(String.valueOf(obj2.get("sm_location_name")));
        return city;
    }

}