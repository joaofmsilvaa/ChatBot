package com.example.chatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class KnownInput {

    @PrimaryKey(autoGenerate = true)
    int inputId;

    String input;

    boolean returnExercise;

    public KnownInput(int inputId, String input, Boolean returnExercise) {
        this.inputId = inputId;
        this.input = input;
        this.returnExercise = returnExercise;
    }

    public int getInputId() {
        return inputId;
    }

    public String getInput() {
        return input;
    }

}
