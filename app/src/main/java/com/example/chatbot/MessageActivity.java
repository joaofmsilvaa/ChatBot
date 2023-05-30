package com.example.chatbot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MessageActivity extends AppCompatActivity {

    private MessageAdapter adapter;
    private EditText messageEditText;
    private FloatingActionButton sendMessageFAB;
    private FloatingActionButton backFB;
    private  LinearLayoutManager layoutManager;
    private int currentExercise = 0;
    private Boolean exerciseIndicator = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        messageEditText = findViewById(R.id.messageEditText);
        sendMessageFAB = findViewById(R.id.sendMessageActionButton);
        backFB = findViewById(R.id.backfloatingActionButton);

        backFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToChatMenu(MessageActivity.this);
            }
        });

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
        this.adapter = new MessageAdapter(messageDAO.getAll(chatId));


        // criar um objecto do tipo LinearLayoutManager para ser utilizado na RecyclerView
        // o LinearLayoutManager tem como orientação default a orientação Vertical
        this.layoutManager = new LinearLayoutManager(this);

        // Definir que a RecyclerView utiliza como Adapter o objeto que criámos anteriormente
        recyclerView.setAdapter(this.adapter);
        // Definir que a RecyclerView utiliza como LayoutManager o objeto que criámos anteriormente
        recyclerView.setLayoutManager(this.layoutManager);


        sendMessageFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int personSenderId = 0;

                String message = messageEditText.getText().toString();

                // Learned the method trim() in
                // https://stackoverflow.com/questions/3247067/how-do-i-check-that-a-java-string-is-not-all-whitespaces

                if (message.trim().length() > 0){
                    messageEditText.setText("");

                    Calendar calendar = Calendar.getInstance();
                    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

                    String hour = currentHour + ":" + minuteFormater(calendar);

                    currentDate += " " + hour;

                    Message newMessage = new Message(0,chatId, personSenderId , false , 0 ,message,currentDate);

                    messageDAO.insert(newMessage);

                    db.getChatDao().updateLastMessageDate(currentDate,chatId);
                    db.getChatDao().updateLastMessage(message, chatId);

                    List<Message> newMessageList = db.getMessageDao().getAll(chatId);
                    MessageActivity.this.adapter.refreshList(newMessageList, MessageActivity.this);

                    botAnswer(chatId, currentDate, message, messageDAO, db);

                    // Source: https://stackoverflow.com/questions/27016547/how-to-keep-recyclerview-always-scroll-bottom
                    layoutManager.setStackFromEnd(true);

                }
            }
        });
    }

    public static String minuteFormater(Calendar calendar){
        int currentMinute = calendar.get(Calendar.MINUTE);
        String minutes = Integer.toString(currentMinute);


        if(currentMinute < 10){
            minutes = "0" + currentMinute;
        }

        return minutes;
    }

    public void botAnswer(int chatId, String currentDate, String message, MessageDAO messageDAO, AppDatabase db){
        int botSenderId = 1;

        String botMessage = generateAnswers(message, this.exerciseIndicator);

        Message newMessage = new Message(0, chatId , botSenderId , this.exerciseIndicator , this.currentExercise , botMessage , currentDate);

        messageDAO.insert(newMessage);

        db.getChatDao().updateLastMessageDate(currentDate,chatId);
        db.getChatDao().updateLastMessage(botMessage, chatId);

        List<Message> newMessageList = db.getMessageDao().getAll(chatId);
        MessageActivity.this.adapter.refreshList(newMessageList, MessageActivity.this);
    }


    public static void backToChatMenu(Context context){

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

    }

    public String generateAnswers(String message, Boolean exerciseIndicator) {
        this.exerciseIndicator = false;

        AppDatabase db = AppDatabase.getInstance(this);
        ExerciseDao exerciseDao = db.getExerciseDao();

        if(!this.exerciseIndicator && this.currentExercise == 0){
            message = messageAnalyze(message, exerciseDao);
        }
        else if(this.currentExercise != 0){
            message = exerciseAnalyze(message,exerciseDao);
        }


        return message;

    }


    public static String exercises(String botMessage, int currentExercise, Context context){
        AppDatabase db = AppDatabase.getInstance(context);
        ExerciseDao exerciseDao = db.getExerciseDao();

        botMessage = exerciseDao.getExerciseQuestion(currentExercise);

        return botMessage;
    }


    public String messageAnalyze(String message, ExerciseDao exerciseDao) {

        AppDatabase db = AppDatabase.getInstance(this);
        KnownInputDao knownInputDao = db.getKnowninputDao();
        OutputDao outputDao = db.getOutputDao();

        int ammountSimilar = knownInputDao.countSimilar(message);

        if (ammountSimilar > 0) {
            int inputId = knownInputDao.getInputId(message);

            List<String> outputList = outputDao.getOutputForId(inputId);

            int rnd = new Random().nextInt(outputList.size());

            message = outputList.get(rnd);

        } else if ("ask me a question".equals(message) || "give me a exercise".equals(message) || "ask me something".equals(message)) {
            this.currentExercise = new Random().nextInt(exerciseDao.getAmmountOfExercises());
            this.exerciseIndicator = true;

            message = exercises(message, this.currentExercise, this);
        }

        return message;
    }

    public String exerciseAnalyze(String message, ExerciseDao exerciseDao){
        String[] greatAnswers = {"Great, you're right", "Nice, you got it", "Well done, you did great"};
        String[] badAnswers = {"Incorrect, the answer was option ", "Nope, it was option ", "Good luck next time, it was option "};

        String correctAnswer = exerciseDao.getCorrectAnswer(this.currentExercise);

        if(message.equals(correctAnswer) || message.equals(correctAnswer.toLowerCase(Locale.ROOT))){
            int randomReaction = new Random().nextInt(greatAnswers.length);
            message = greatAnswers[randomReaction];
            this.currentExercise = 0;
            this.exerciseIndicator = false;
        }
        else{
            int randomReaction = new Random().nextInt(badAnswers.length);
            message = badAnswers[randomReaction] + "\"" + exerciseDao.getCorrectAnswer(this.currentExercise) + "\"";
            this.currentExercise = 0;
            this.exerciseIndicator = false;
        }

        return message;
    }
}
