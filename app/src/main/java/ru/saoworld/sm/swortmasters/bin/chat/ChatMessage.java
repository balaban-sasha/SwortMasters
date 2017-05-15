package ru.saoworld.sm.swortmasters.bin.chat;

public class ChatMessage {
    public boolean left;
    public String message;
    public String userLogin;

    public ChatMessage(boolean left , String message,String userLogin) {
        // TODO Auto-generated constructor stub
        super();
        this.left=left;
        this.message = message;
        this.userLogin = userLogin;
    }


}
