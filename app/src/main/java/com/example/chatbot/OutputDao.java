package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OutputDao {

    @Query("SELECT outputString FROM output WHERE inputId = :id")
    List<String> getOutputForId(int id);

}
