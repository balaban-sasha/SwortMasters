package ru.saoworld.sm.swortmasters.bin.subsidiary.teleport.config;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.bean.City;
import ru.saoworld.sm.swortmasters.dao.impl.shop.impl.ShopDAO;
import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;


public class CityTeleportAdapter extends ArrayAdapter<City> {

    private TextView cityName;
    private Button tpRequest;
    private List<City> cityList = new ArrayList<City>();
    private LinearLayout layout;
    private static UserDAO userDAO;
    static int userId;
    public CityTeleportAdapter(int id, Context context, int textViewReourceId)
    {
        super(context,textViewReourceId);
        this.userId=id;
    }

    static {

        userDAO = new UserDAO();
    }
    public void add(City object) {
        cityList.add(object);
        super.add(object);
    }
    public int getCount()
    {
        return this.cityList.size();
    }
    public City getItem(int index){

        return this.cityList.get(index);
    }
    public View getView(final int position, View ConvertView, ViewGroup parent){

        View v = ConvertView;
        if(v==null){

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.single_city, parent,false);

        }

        layout = (LinearLayout)v.findViewById(R.id.single_city);
        City cityObj = getItem(position);
        cityName =(TextView)v.findViewById(R.id.cityName);
        tpRequest = (Button) v.findViewById(R.id.tpRequestButton);
        tpRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {try {
                if (userDAO.changeUserLocation(userId,getItem(position).getCityId()))
                    Toast.makeText(getContext(), getItem(position).getCityName().toString(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                new DataException(e);
            }
            }
        });
        cityName.setText(cityObj.getCityName());
        layout.setGravity(Gravity.LEFT);
        return v;
    }

}
