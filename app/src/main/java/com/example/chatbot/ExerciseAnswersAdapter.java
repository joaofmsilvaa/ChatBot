package com.example.chatbot;

import android.annotation.SuppressLint;
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

import java.util.List;


public class ExerciseAnswersAdapter extends RecyclerView.Adapter<com.example.chatbot.ExerciseAnswersAdapter.ExerciseAnswersViewHolder> {


        private List<ExerciseAnswers> exerciseAnswersList;
        private int exerciseId;


        public ExerciseAnswersAdapter(List<ExerciseAnswers> exerciseAnswersList, int exerciseId) {
            this.exerciseId = exerciseId;
            this.exerciseAnswersList = exerciseAnswersList;

        }

        @NonNull
        @Override
        public ExerciseAnswersAdapter.ExerciseAnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Criar um objeto do tipo View com base no layout criado (chat_item.xml)
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
            // criar e devolver um objeto do tipo ContactViewHolder
            return new ExerciseAnswersAdapter.ExerciseAnswersViewHolder(rootView, parent.getContext());
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ExerciseAnswersAdapter.ExerciseAnswersViewHolder holder, int position) {
            AppDatabase db = AppDatabase.getInstance(holder.context);
            ExerciseDao exerciseDao = db.getExerciseDao();
            final ExerciseAnswers ExerciseAnswers = this.exerciseAnswersList.get(position);

            holder.optionLetterTextView.setText(ExerciseAnswers.getOptionLetter() + " - ");
            holder.optionPhraseTextView.setText(ExerciseAnswers.getOptionText());

        }

        @Override
        public int getItemCount() {
            return exerciseAnswersList.size();
        }


        public class ExerciseAnswersViewHolder extends RecyclerView.ViewHolder {

            private Context context;
            private View rootView;

            public ConstraintLayout constraintLayout;

            private TextView optionLetterTextView;
            private TextView optionPhraseTextView;


            public ExerciseAnswersViewHolder(@NonNull View rootView, Context context) {
                super(rootView);
                this.context = context;
                this.rootView = rootView;
                this.constraintLayout = this.rootView.findViewById(R.id.optionsConstraintLayout);
                this.optionLetterTextView = this.rootView.findViewById(R.id.optionLetterTextView);
                this.optionPhraseTextView = this.rootView.findViewById(R.id.optionPhraseTextView);

            }
        }
    }


