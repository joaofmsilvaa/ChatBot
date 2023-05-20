package com.example.chatbot;

import android.content.Context;
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

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Criar um objeto do tipo View com base no layout criado (message_item.xml)
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        // criar e devolver um objeto do tipo ContactViewHolder
        return new MessageAdapter.MessageViewHolder(rootView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        AppDatabase db = AppDatabase.getInstance(holder.context);

        final Message messageList = this.messageList.get(position);

        String chatName = db.getChatDao().getNameByID(messageList.getChatId());

        holder.dateTextView.setText(messageList.getDate());
        holder.authorNameTextView.setText(chatName);
        holder.messageTextView.setText(messageList.getMessage());

        String picture = db.getChatDao().getPictureByChatId(messageList.getChatId());
        Glide.with(holder.rootView.getContext()).load(picture).into(holder.authorImageView);


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private View rootView;
        private TextView authorNameTextView;
        private TextView messageTextView;
        private TextView dateTextView;

        private ImageView authorImageView;

        public MessageViewHolder(@NonNull View rootView, Context context) {
            super(rootView);
            this.context = context;
            this.rootView = rootView;
            this.authorNameTextView = rootView.findViewById(R.id.authorNameTextView);
            this.messageTextView = rootView.findViewById(R.id.messageTextView);
            this.dateTextView = rootView.findViewById(R.id.dateTextView);
            this.authorImageView = rootView.findViewById(R.id.authorImageView);
        }
    }

    public void refreshList(List<Message> newMessageList) {
        this.messageList = newMessageList;
        notifyDataSetChanged();
    }

}
