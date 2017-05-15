package ru.saoworld.sm.swortmasters.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.saoworld.sm.swortmasters.dao.exception.DAOException;
import ru.saoworld.sm.swortmasters.exception.DataException;


public class AbstractDAOController<Entity, PrimaryKey> implements DAOController<Entity, PrimaryKey> {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected OkHttpClient client = new OkHttpClient();

    private int auth(String login, String password) throws IOException, JSONException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("email", login);
        data.put("password", password);
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/login.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return Integer.parseInt(response.body().string());
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    public ArrayList<Entity> getTable(String sql) throws DAOException {
        return null;
    }


    @Override
    public int checkOnRightLoginData(String login, String password) {
        int request = 0;
        try {
            request = auth(login, password);
        } catch (Exception e) {
            new DataException(e);
        }
        return request;
    }

    private Boolean checkIfEmailExist(String email) throws IOException {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("email", email);
        JSONObject obj = new JSONObject(data);
        RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
        Request request = new Request.Builder()
                .url("http://swordmaster.saoworld.ru/checkIfUserExist.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String answear = "";
        answear = response.body().string();
        if (answear.equals("ok"))
            return true;
        else return false;
    }
    @Override
    public Boolean userRegistry(String login, String password, String email) throws IOException {

        if (checkIfEmailExist(email)) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("email", email);
            data.put("login", login);
            data.put("password", password);
            JSONObject obj = new JSONObject(data);
            RequestBody body = RequestBody.create(JSON, String.valueOf(obj));
            Request request = new Request.Builder()
                    .url("http://swordmaster.saoworld.ru/registr.php")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Entity getOneField(String sql) throws DAOException {
        return null;
    }

}
