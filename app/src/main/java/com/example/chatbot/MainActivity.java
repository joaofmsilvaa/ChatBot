package com.example.chatbot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ChatAdapter.ChatAdapterEventListener{

    private ChatAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addChatButton = findViewById(R.id.addNewChatButton);

        addChatButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddChatActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


        // obter uma referência para a RecyclerView que existe no layout da MainActivity
        RecyclerView recyclerView = findViewById(R.id.chatsRecyclerView);

        // obter uma instância do ChattDao e do Message
        AppDatabase db = AppDatabase.getInstance(this);
        ChatDAO chatDao = db.getChatDao();
        MessageDAO messageDAO = db.getMessageDao();

        // criar um objeto do tipo ChatAdapter (que extende Adapter)
        this.adapter = new ChatAdapter(chatDao.getAll(),messageDAO.getLastMessage(), this);
        // ContactAdapter adapter = new ContactAdapter(AppDatabase.getInstance(this).getContactDao().getAll());

        // criar um objecto do tipo LinearLayoutManager para ser utilizado na RecyclerView
        // o LinearLayoutManager tem como orientação default a orientação Vertical
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        // Definir que a RecyclerView utiliza como Adapter o objeto que criámos anteriormente
        recyclerView.setAdapter(this.adapter);
        // Definir que a RecyclerView utiliza como LayoutManager o objeto que criámos anteriormente
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Chat> newChatList = AppDatabase.getInstance(this).getChatDao().getAll();
        this.adapter.refreshList(newChatList);

    }

    @Override
    public void onChatClicked(int chatId) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("chatIDKey", chatId);
        this.startActivity(intent);
    }

    @Override
    public void onContactLongClicked(int chatId) {
        ChatDAO chatDao = AppDatabase.getInstance(MainActivity.this).getChatDao();
        Chat chat = chatDao.getChatById(chatId);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete chat");
        builder.setMessage("Do you really want to delete \"" + chat.getChatName() + "\" ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chatDao.delete(chat);
                List<Chat> newList = chatDao.getAll();
                adapter.refreshList(newList);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}