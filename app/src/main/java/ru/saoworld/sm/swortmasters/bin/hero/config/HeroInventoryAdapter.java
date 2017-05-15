package ru.saoworld.sm.swortmasters.bin.hero.config;

import android.content.Context;
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
import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

/**
 * Created by Саша on 14.05.2017.
 */

public class HeroInventoryAdapter extends ArrayAdapter<GameItem> {

    private TextView itemNameText;
    private TextView itemDescriptionText;
    private TextView itemCharacteristicText;
    private Button itemSellRequest;
    static UserDAO userDao;
    private List<GameItem> itemList = new ArrayList<GameItem>();
    private RelativeLayout layout;
    static int userId;

    public HeroInventoryAdapter(int id, Context context, int textViewReourceId) {
        super(context, textViewReourceId);
        this.userId = id;
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

        userDao = new UserDAO();
    }

    public View getView(final int position, View ConvertView, ViewGroup parent) {

        View v = ConvertView;
        if (v == null) {

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_inventory_item, parent, false);

        }

        layout = (RelativeLayout) v.findViewById(R.id.single_inventory_item);
        GameItem cityObj = getItem(position);
        itemSellRequest = (Button) v.findViewById(R.id.itemSellRequest);
        itemSellRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (userDao.deleteItemFromUser(userId,getItem(position).getItemId())) {
                        Toast.makeText(getContext(), "You had sell : " + getItem(position).getName(), Toast.LENGTH_LONG).show();
                        itemList.remove(getItem(position));
                        notifyDataSetChanged();
                    }
                        else
                        Toast.makeText(getContext(), "Try again later!", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    new DataException(e);
                }
            }
        });
        itemNameText = (TextView) v.findViewById(R.id.itemNameText);
        itemDescriptionText = (TextView) v.findViewById(R.id.itemDescriptionText);
        itemCharacteristicText = (TextView) v.findViewById(R.id.itemCharacteristicText);
        itemNameText.setText(cityObj.getName());
        itemDescriptionText.setText(cityObj.getDescrpiption());
        itemCharacteristicText.setText(cityObj.toString());
        layout.setGravity(Gravity.LEFT);
        return v;
    }

}
