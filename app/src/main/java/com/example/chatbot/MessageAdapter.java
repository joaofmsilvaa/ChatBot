package com.example.chatbot;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);

        if(!message.exerciseMessage){
            // If the message isn't a exercise it returns the sender id
            return message.getSenderId();
        }
        else{
            // If the message is a exercise it returns the value 2 to differ from the senderIds
            return 2;
        }
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            // Criar um objeto do tipo View com base no layout criado (message_item.xml)
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagersent_item, parent, false);
            // criar e devolver um objeto do tipo ContactViewHolder
            return new MessageAdapter.MessageSentViewHolder(rootView, parent.getContext());
        }

        else if(viewType == 1) {
            // Criar um objeto do tipo View com base no layout criado (message_item.xml)
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagereceived_item, parent, false);
            // criar e devolver um objeto do tipo ContactViewHolder
            return new MessageAdapter.MessageReceivedViewHolder(rootView, parent.getContext());
        }

        else{
            // Criar um objeto do tipo View com base no layout criado (message_item.xml)
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
            // criar e devolver um objeto do tipo ContactViewHolder
            return new MessageAdapter.ExerciseViewHolder(rootView, parent.getContext());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        AppDatabase db = AppDatabase.getInstance(holder.context);

        final Message messageList = this.messageList.get(position);

        String chatName = db.getChatDao().getNameByID(messageList.getChatId());

        if(messageList.senderId == 0){
            chatName = "You";
        }

        holder.dateTextView.setText(messageList.getDate());
        holder.authorNameTextView.setText(chatName);
        holder.messageTextView.setText(messageList.getMessage());
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

        private TextView questionTextView;
        private RecyclerView optionsRecyclerView;

        public MessageViewHolder(@NonNull View rootView, Context context) {
            super(rootView);
            this.context = context;
            this.rootView = rootView;
            this.authorNameTextView = this.rootView.findViewById(R.id.authorNameTextView);
            this.messageTextView = this.rootView.findViewById(R.id.messageTextView);
            this.dateTextView = this.rootView.findViewById(R.id.dateTextView);

            this.questionTextView = this.rootView.findViewById(R.id.questionTextView);
            this.optionsRecyclerView = this.rootView.findViewById(R.id.optionsRecyclerView);
        }
    }

    public class MessageReceivedViewHolder extends MessageViewHolder {
        public MessageReceivedViewHolder(@NonNull View rootView, Context context) {
            super(rootView, context);
        }
    }

    public class MessageSentViewHolder extends MessageViewHolder {
        public MessageSentViewHolder(@NonNull View rootView, Context context) {
            super(rootView, context);
        }
    }

    public class ExerciseViewHolder extends  MessageViewHolder{
        public ExerciseViewHolder(@NonNull View rootView, Context context){
            super(rootView, context);
        }
    }

    public void refreshList(List<Message> newMessageList, Context context) {

        this.messageList = newMessageList;
        notifyDataSetChanged();
    }



}
