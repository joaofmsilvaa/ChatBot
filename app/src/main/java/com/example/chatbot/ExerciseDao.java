package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM Exercise")
    List<Exercise> getAll();

    @Query("SELECT exerciseQuestion FROM Exercise WHERE exerciseId = :id")
    String getExerciseQuestion(int id);

    @Query("SELECT correctAnswer FROM Exercise WHERE exerciseId = :id")
    String getCorrectAnswer(int id);

    @Query("SELECT COUNT(exerciseId) FROM Exercise")
    int getAmmountOfExercises();
}
