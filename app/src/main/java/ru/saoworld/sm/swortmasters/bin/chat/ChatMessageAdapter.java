package ru.saoworld.sm.swortmasters.bin.chat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.saoworld.sm.swortmasters.R;


public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private TextView userLogin;
    private List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
    private LinearLayout layout;
    public ChatMessageAdapter(Context context, int textViewReourceId)
    {
        super(context,textViewReourceId);
    }

    public void add(ChatMessage object) {
        // TODO Auto-generated method stub

        MessageList.add(object);
        super.add(object);
    }
    public int getCount()
    {
        return this.MessageList.size();
    }
    public ChatMessage getItem(int index){

        return this.MessageList.get(index);
    }
    public View getView(int position, View ConvertView, ViewGroup parent){

        View v = ConvertView;
        if(v==null){

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.single_message, parent,false);

        }

        layout = (LinearLayout)v.findViewById(R.id.single_message);
        ChatMessage messageobj = getItem(position);
        chatText =(TextView)v.findViewById(R.id.SingleMessage);
        userLogin = (TextView)v.findViewById(R.id.UserLogin);
        chatText.setText(messageobj.message);
        userLogin.setText(messageobj.userLogin);
        layout.setGravity(messageobj.left? Gravity.LEFT:Gravity.RIGHT);
        return v;
    }

}
