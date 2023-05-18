package com.example.chatbot;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    int messageId;
    int chatId;
    String message;
    String date;

    public Message(int messageId, int chatId ,String message, String date) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.message = message;
        this.date = date;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getChatId(){
        return chatId;
    }
    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
