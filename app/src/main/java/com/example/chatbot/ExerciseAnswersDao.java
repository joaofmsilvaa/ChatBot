package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseAnswersDao {

    @Query("SELECT * FROM ExerciseAnswers")
    List<ExerciseAnswers> getAll();

}
