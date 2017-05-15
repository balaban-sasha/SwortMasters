package ru.saoworld.sm.swortmasters.bin.subsidiary.shop.config;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.saoworld.sm.swortmasters.R;
import ru.saoworld.sm.swortmasters.bin.bean.GameItem;
import ru.saoworld.sm.swortmasters.dao.impl.shop.impl.ShopDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

import static ru.saoworld.sm.swortmasters.R.id.cityName;
import static ru.saoworld.sm.swortmasters.R.id.itemNameText;

public class ShopAdapter extends ArrayAdapter<GameItem> {

    private TextView itemNameText;
    private TextView itemDescriptionText;
    private TextView itemCharacteristicText;
    static ShopDAO shopDAO;
    private Button itemBuyRequest;
    private List<GameItem> itemList = new ArrayList<GameItem>();
    private RelativeLayout layout;
    static int userId;
    public ShopAdapter(int id, Context context, int textViewReourceId) {
        super(context, textViewReourceId);
        this.userId=id;
    }

    public void add(GameItem object) {
        itemList.add(object);
        super.add(object);
    }

    public int getCount() {
        return this.itemList.size();
    }

    public GameItem getItem(int index) {

        return this.itemList.get(index);
    }


    static {

        shopDAO = new ShopDAO();
    }

    public View getView(final int position, View ConvertView, ViewGroup parent) {

        View v = ConvertView;
        if (v == null) {

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_item, parent, false);

        }

        layout = (RelativeLayout) v.findViewById(R.id.single_item);
        GameItem cityObj = getItem(position);
        itemNameText = (TextView) v.findViewById(R.id.itemNameText);
        itemDescriptionText = (TextView) v.findViewById(R.id.itemDescriptionText);
        itemCharacteristicText = (TextView) v.findViewById(R.id.itemCharacteristicText);
        itemBuyRequest = (Button) v.findViewById(R.id.itemBuyRequest);
        itemBuyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (shopDAO.addItemToUser(userId,getItem(position).getItemId()))
                        Toast.makeText(getContext(), "You had buy : "+getItem(position).getName(), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(), "May be you have item with same type or you don't have enough money!", Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    new DataException(e);
                }
            }
        });
        itemNameText.setText(cityObj.getName());
        itemDescriptionText.setText(cityObj.getDescrpiption());
        itemCharacteristicText.setText(cityObj.toString());
        layout.setGravity(Gravity.LEFT);
        return v;
    }

}
