package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDAO {

    @Query("SELECT * FROM chat")
    List<Chat> getAll();


    @Query("SELECT chat.* FROM Chat, message WHERE message.chatID = :id AND message.chatId = chat.chatID")
    List<Chat> getNameByID(int id);

    @Insert
    void insert(Chat chat);
}
