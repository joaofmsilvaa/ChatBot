package com.example.chatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"answerId", "exerciseId"})
public class ExerciseAnswers {

    int answerId;

    int exerciseId;

    String optionLetter;

    String optionText;

    public ExerciseAnswers(int answerId, int exerciseId, String optionLetter, String optionText) {
        this.answerId = answerId;
        this.exerciseId = exerciseId;
        this.optionLetter = optionLetter;
        this.optionText = optionText;
    }

    public int getAnswerId() {
        return answerId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getOptionLetter() {
        return optionLetter;
    }

    public String getOptionText() {
        return optionText;
    }
}
