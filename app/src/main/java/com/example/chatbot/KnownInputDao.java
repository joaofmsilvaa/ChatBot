package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface KnownInputDao {

    @Query("SELECT COUNT(*) FROM knowninput WHERE input = :message")
    int countSimilar(String message);


    @Query("SELECT inputId FROM knowninput WHERE input = :message")
    int getInputId(String message);

}
