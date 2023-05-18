package com.example.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessageActivity extends AppCompatActivity {

    private String chatKey = "chatIDkey";
    private MessageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int chatId = bundle.getInt("chatIDKey");

        // obter uma referência para a RecyclerView que existe no layout da chatActivity
        RecyclerView recyclerView = findViewById(R.id.messagesRecyclerView);

        // obter uma instância do messageDAO
        AppDatabase db = AppDatabase.getInstance(this);
        ChatDAO chatDao = db.getChatDao();
        MessageDAO messageDAO = db.getMessageDao();

        // criar um objeto do tipo MessageAdapter (que extende Adapter)
        this.adapter = new MessageAdapter(messageDAO.getAll(chatId), chatDao.getNameByID(chatId));
        // ContactAdapter adapter = new ContactAdapter(AppDatabase.getInstance(this).getContactDao().getAll());

        // criar um objecto do tipo LinearLayoutManager para ser utilizado na RecyclerView
        // o LinearLayoutManager tem como orientação default a orientação Vertical
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Definir que a RecyclerView utiliza como Adapter o objeto que criámos anteriormente
        recyclerView.setAdapter(this.adapter);
        // Definir que a RecyclerView utiliza como LayoutManager o objeto que criámos anteriormente
        recyclerView.setLayoutManager(layoutManager);

    }
}
