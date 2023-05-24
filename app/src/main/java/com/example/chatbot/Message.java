package com.example.chatbot;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    int messageId;
    int chatId;

    // If the id is equal to "0" the message was sent by the person
    // If the id is equal to "1" the message was sent by the bot
    int senderId;

    String message;
    String date;

    public Message(int messageId, int chatId , int senderId ,String message, String date) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.senderId = senderId;
        this.message = message;
        this.date = date;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getChatId(){
        return chatId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
