package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.EventListener;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

    private List<Chat> chatList;
    private List<Message> messageList;

    private ChatAdapterEventListener eventListener;

    public ChatAdapter(List<Chat> chatList, List<Message> messageList, ChatAdapterEventListener eventListener) {
        // armazenar na variável de instância o valor do parâmetro do construtor
        this.chatList = chatList;
        this.messageList = messageList;
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Criar um objeto do tipo View com base no layout criado (chat_item.xml)
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        // criar e devolver um objeto do tipo ContactViewHolder
        return new ChatViewHolder(rootView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        final Chat chat = this.chatList.get(position);

        Message lastMessage = AppDatabase.getInstance(holder.context).getMessageDao().getLastMessageFromChat(chat.getChatID());

        Glide.with(holder.rootView.getContext()).load(chat.getChatImage()).into(holder.chatImageView);
        holder.chatNameTextView.setText(chat.getChatName());

        if (lastMessage != null) {
            holder.lastMessageTextView.setText(lastMessage.getMessage());
        } else {
            holder.lastMessageTextView.setText("");
        }

        holder.chatConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventListener != null) eventListener.onChatClicked(chat.getChatID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private View rootView;
        private TextView chatNameTextView;
        private ImageView chatImageView;
        private TextView lastMessageTextView;

        public ConstraintLayout chatConstraintLayout;
        public ChatViewHolder(@NonNull View rootView, Context context) {
            super(rootView);
            this.context = context;
            this.rootView = rootView;
            this.chatImageView = rootView.findViewById(R.id.chatImageView);
            this.chatNameTextView = rootView.findViewById(R.id.chatNameTextView);
            this.lastMessageTextView = rootView.findViewById(R.id.lastMessageTextView);
            this.chatConstraintLayout = rootView.findViewById(R.id.chatConstraintLayout);
        }
    }

    public interface ChatAdapterEventListener {
        void onChatClicked(int chatId);
        void onContactLongClicked(int chatId);
    }

    public void refreshList(List<Chat> newChatList) {
        this.chatList = newChatList;
        notifyDataSetChanged();
    }
}

