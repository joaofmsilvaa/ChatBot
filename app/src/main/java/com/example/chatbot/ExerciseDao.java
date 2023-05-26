package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM Exercise")
    List<Exercise> getAll();


}
