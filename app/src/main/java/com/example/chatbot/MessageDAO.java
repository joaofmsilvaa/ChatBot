package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDAO {

    @Query("SELECT * FROM message WHERE chatId = :id")
    List<Message> getAll(int id);

    @Query("SELECT * FROM message WHERE chatId = :id ORDER BY date desc limit 1")
    Message getLastMessageFromChat(int id);

    @Query("SELECT * FROM message ORDER BY date DESC ")
    List<Message> getLastMessage();
}
