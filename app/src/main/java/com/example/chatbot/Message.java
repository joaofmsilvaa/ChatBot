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

    // If the value is equal to "false" the message is a normal message
    // If the value is equal to "true" it is a exercise
    Boolean exerciseMessage;

    String message;
    String date;

    public Message(int messageId, int chatId , int senderId , Boolean exerciseMessage ,String message, String date) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.senderId = senderId;
        this.exerciseMessage = exerciseMessage;
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

    public Boolean getExerciseMessage(){
        return exerciseMessage;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
