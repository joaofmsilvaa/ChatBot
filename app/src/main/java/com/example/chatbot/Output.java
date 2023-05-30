package com.example.chatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Output {

    @PrimaryKey(autoGenerate = true)
    int outputId;

    int inputId;

    String outputString;

    public Output(int outputId, int inputId, String outputString) {
        this.outputId = outputId;
        this.inputId = inputId;
        this.outputString = outputString;
    }

    public int getOutputId() {
        return outputId;
    }

    public int getInputId() {
        return inputId;
    }

    public String getOutputString() {
        return outputString;
    }

}
