package com.example.chatbot;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

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

        // register the UI widgets with their appropriate IDs
        pickImageButton = findViewById(R.id.pickImageButton);
        previewImageView = findViewById(R.id.previewImageView);

        // handle the Choose Image button to trigger
        // the image chooser function
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }


    /*
    Source: https://stackoverflow.com/questions/10165302/dialog-to-pick-image-from-gallery-or-from-camera
     */

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                Log.i("imageURI", selectedImageUri.toString());
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    previewImageView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public void createChat(String chatName, Context context){
        AppDatabase db = AppDatabase.getInstance(context);
        ChatDAO chatDao = db.getChatDao();

        String chatImage = "https://www.w3schools.com/howto/img_avatar.png";

        Chat chat = new Chat(0,chatName,"",chatImage);

        chatDao.insert(chat);
        finish();

    }
}
