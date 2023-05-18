package com.example.chatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Chat {

    @PrimaryKey(autoGenerate = true)
    private int chatID;
    private String chatName;
    public String lastMessageDate;
    private String lastMessage;
    private String chatImage;

    public Chat(int chatID, String chatName, String lastMessage, String chatImage) {
        this.chatID = chatID;
        this.chatName = chatName;
        this.lastMessage = lastMessage;
        this.chatImage = chatImage;
    }

    public int getChatID(){
        return chatID;
    }

    public String getChatName() {
        return chatName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getChatImage() {
        return chatImage;
    }
}
