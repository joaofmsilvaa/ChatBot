package com.example.chatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    int exerciseId;

    String exerciseQuestion;

    String correctAnswer;

    public Exercise(int exerciseId, String exerciseQuestion, String correctAnswer) {
        this.exerciseId = exerciseId;
        this.exerciseQuestion = exerciseQuestion;
        this.correctAnswer = correctAnswer;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getExerciseQuestion() {
        return exerciseQuestion;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
