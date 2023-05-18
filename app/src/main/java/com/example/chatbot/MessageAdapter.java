package com.example.chatbot;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    List<Message> messageList;
    List<Chat> chatList;

    public MessageAdapter(List<Message> messageList, List<Chat> chatList) {
        this.messageList = messageList;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Criar um objeto do tipo View com base no layout criado (message_item.xml)
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        // criar e devolver um objeto do tipo ContactViewHolder
        return new MessageAdapter.MessageViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        final Message messageList = this.messageList.get(position);
        final Chat chat = this.chatList.get(position);

        Glide.with(holder.rootView.getContext()).load(chat.getChatImage()).into(holder.authorImageView);
        holder.authorNameTextView.setText(chat.getChatName());
        holder.dateTextView.setText(messageList.getDate());
        holder.messageTextView.setText(messageList.getMessage());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private View rootView;
        private TextView authorNameTextView;
        private TextView messageTextView;
        private TextView dateTextView;

        private ImageView authorImageView;

        public MessageViewHolder(@NonNull View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.authorNameTextView = rootView.findViewById(R.id.authorNameTextView);
            this.messageTextView = rootView.findViewById(R.id.messageTextView);
            this.dateTextView = rootView.findViewById(R.id.dateTextView);
            this.authorImageView = rootView.findViewById(R.id.authorImageView);
        }
    }

}
