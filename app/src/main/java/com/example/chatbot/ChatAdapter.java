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

import org.w3c.dom.Text;

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

        Glide.with(holder.rootView.getContext()).load(R.drawable.botimage_01).into(holder.chatImageView);
        holder.chatNameTextView.setText(chat.getChatName());
        holder.lastMessageDateTV.setText(chat.getLastMessageDate());
        holder.lastMessageTextView.setText(chat.getLastMessage());

        if (chat.getLastMessage() != null) {
            holder.lastMessageTextView.setText(chat.getLastMessage());
        } else {
            holder.lastMessageTextView.setText("");
        }

        holder.chatConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventListener != null) eventListener.onChatClicked(chat.getChatID());
            }
        });

        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (eventListener != null) {
                    eventListener.onContactLongClicked(chat.getChatID());
                    return true;
                } else {
                    return false;
                }
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
        private TextView lastMessageDateTV;
        private ImageView chatImageView;
        private TextView lastMessageTextView;

        public ConstraintLayout chatConstraintLayout;
        public ChatViewHolder(@NonNull View rootView, Context context) {
            super(rootView);
            this.context = context;
            this.rootView = rootView;
            this.chatImageView = rootView.findViewById(R.id.chatImageView);
            this.chatNameTextView = rootView.findViewById(R.id.chatNameTextView);
            this.lastMessageDateTV = rootView.findViewById(R.id.lastMessageDateTV);
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

