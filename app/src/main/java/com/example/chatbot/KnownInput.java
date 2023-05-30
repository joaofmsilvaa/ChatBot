package com.example.chatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class KnownInput {

    @PrimaryKey(autoGenerate = true)
    int inputId;

    String input;

    public KnownInput(int inputId, String input) {
        this.inputId = inputId;
        this.input = input;
    }

    public int getInputId() {
        return inputId;
    }

    public String getInput() {
        return input;
    }

}
