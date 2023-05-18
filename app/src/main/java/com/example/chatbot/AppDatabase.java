package com.example.chatbot;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Chat.class, Message.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ChatDAO getChatDao();
    public abstract MessageDAO getMessageDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDatabase").allowMainThreadQueries()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            db.execSQL("INSERT INTO Chat ('chatName','lastMessage', 'chatImage') VALUES ('Bot 1', '' , 'https://www.w3schools.com/howto/img_avatar.png')");
                            db.execSQL("INSERT INTO Chat ('chatName', 'LastMessage','chatImage') VALUES ('Bot 2', '' , 'https://www.w3schools.com/howto/img_avatar.png')");

                            db.execSQL("INSERT INTO MESSAGE('chatId','message', 'date') VALUES (0, 'Hello world', '2023-05-17')");
                            db.execSQL("INSERT INTO MESSAGE('chatId','message', 'date' ) VALUES (1, 'Hello word', '2023-05-17')");
                            /*
                            db.execSQL("INSERT INTO MESSAGE('chatId','message', 'date') VALUES (1, 'Hello mundo', '2023-05-18')");
                            */
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }
}
