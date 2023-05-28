package com.example.chatbot;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Chat.class, Message.class, Exercise.class, ExerciseAnswers.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ChatDAO getChatDao();
    public abstract MessageDAO getMessageDao();
    public abstract ExerciseDao getExerciseDao();
    public abstract ExerciseAnswersDao getExerciseAnswersDao();
    private static AppDatabase INSTANCE;


    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDatabase").allowMainThreadQueries()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            // Exercises

                            db.execSQL("INSERT INTO Exercise VALUES (0 , 'What does the following java code returns?\n " +
                                    "String a = \"Java is cool\";" +
                                    "System.out.println(\"hello world\");', 'A')");

                            db.execSQL("INSERT INTO Exercise VALUES (1 ,'What does the \"++\" in a for loop do?\n', 'A')");

                            db.execSQL("INSERT INTO Exercise VALUES (2 ,'Which option successfully uses a method named \"hello\" from a class called \"myClass\"?\n', 'B')");

                            db.execSQL("INSERT INTO Exercise VALUES (3, 'Is String a primitive type in Java?\n', 'B')");


                            // Exercise Answers

                            // Ex1 - answers
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(1, 1, 'A', '\"Hello world\"')");
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(2, 1, 'B', \"Java is cool\")");
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(3, 1, 'C', '\"Syntax error\"')");

                            // Ex2 - Answers
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(4, 2, 'A', 'Increment the value of a certain variable')");
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(5, 2, 'B', 'Does not no anything')");
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(6, 2, 'C', 'Decrement the value of a variable')");

                            // Ex3 - Answers
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(7, 3, 'A', 'hello()')");
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(8, 3, 'B', 'myClass.hello()')");
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(9, 3, 'C', 'hello.myClass()')");

                            // Ex4 - Answers
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(10, 4, 'A', 'Yes')");
                            db.execSQL("INSERT INTO ExerciseAnswers VALUES(11, 4, 'B', 'No')");

                        }
                    })
                    .build();
        }
        return INSTANCE;
    }
}
