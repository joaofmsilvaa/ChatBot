package com.example.chatbot;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.Calendar;

public class AddChatActivity extends AppCompatActivity {

    EditText chatNameEditText;

    Button pickImageButton;


    Button createChatButton;

    ImageView previewImageView;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addchat_activity);

        chatNameEditText = findViewById(R.id.chatNameEditText);

        // Insers the given name and image in the chat entity
        createChatButton = findViewById(R.id.createChatButton);

        createChatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String chatName = chatNameEditText.getText().toString();
                createChat(chatName, AddChatActivity.this);
            }
        });

        previewImageView = findViewById(R.id.previewImageView);

        Glide.with(AddChatActivity.this).load(R.drawable.botimage_01).into(previewImageView);

    }

    public void createChat(String chatName, Context context){
        AppDatabase db = AppDatabase.getInstance(context);
        ChatDAO chatDao = db.getChatDao();

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        String hour = currentHour + ":" + minuteFormater(calendar);

        String chatImage = "R.drawable.botimage_01";

        Chat chat = new Chat(0,chatName,currentDate + " " + hour,"",chatImage);

        chatDao.insert(chat);
        finish();

    }

    public static String minuteFormater(Calendar calendar){
        int currentMinute = calendar.get(Calendar.MINUTE);
        String minutes = Integer.toString(currentMinute);


        if(currentMinute < 10){
            minutes = "0" + currentMinute;
        }

        return minutes;
    }
}
