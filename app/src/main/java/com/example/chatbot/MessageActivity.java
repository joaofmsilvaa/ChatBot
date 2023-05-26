package com.example.chatbot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MessageActivity extends AppCompatActivity {

    private String chatKey = "chatIDkey";
    private MessageAdapter adapter;
    private EditText messageEditText;
    private FloatingActionButton sendMessageFAB;

    private FloatingActionButton backFB;
    private  LinearLayoutManager layoutManager;

    private Context context = MessageActivity.this;

    private int currentExercise = 0;


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

                    Message newMessage = new Message(0,chatId, personSenderId ,message,currentDate);

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
        Boolean exerciseIndicator = false;

        int botSenderId = 1;

        String botMessage = generateAnswers(message, exerciseIndicator);

        Message newMessage = new Message(0, chatId , botSenderId , botMessage , currentDate);

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
        String botMessage = message;

        if(!exerciseIndicator && this.currentExercise == 0){
            if ("hello".equals(message) || "hi".equals(message) || "it's good to see you".equals(message) || "hi there".equals(message)) {
                String[] greedings = {"Hello", "Hi", "Hello, how are you?", "It's good to see you too!", "hi there"};
                int rnd = new Random().nextInt(greedings.length);

                botMessage = greedings[rnd];
            } else if ("how are you?".equals(message) || "what's up?".equals(message) || "how have you been?".equals(message)) {
                String[] checkingIn = {"Good, and you?", "Better now that we're talking  :)", "Good, thanks for asking", "I'm good, can i help you with something?"};
                int rnd = new Random().nextInt(checkingIn.length);

                botMessage = checkingIn[rnd];
            } else if ("good".equals(message) || "i am good".equals(message) || "i am great".equals(message)) {
                String[] checkingIn = {"That's great", "Nice, can i help you something?", "Fantastic!"};
                int rnd = new Random().nextInt(checkingIn.length);

                botMessage = checkingIn[rnd];
            } else if ("i am not feeling good".equals(message) || "i am feeling bad".equals(message) || "i am sad".equals(message) || "bad".equals(message)) {
                String[] checkingIn = {"Do you want me to tell you a joke?", "I'm sorry to hear that", "I'm sorry, can i help you with something?"};
                int rnd = new Random().nextInt(checkingIn.length);

                botMessage = checkingIn[rnd];
            }
            else if("ask me a question".equals(message) || "give me a exercise".equals(message) || "ask me something".equals(message)){
                this.currentExercise = new Random().nextInt(4);
                botMessage = exercises(botMessage,currentExercise);
            }
        }
        else if(this.currentExercise != 0){
            String[] greatAnswers = {"Great, you're correct", "Nice, you got it", "Well done, you did great"};
            String[] badAnswers = {"Incorrect, the answer was option ", "Nope, it was option ", "Good luck next time, it was option"};

            if(this.currentExercise == 1){
                String correctOption = "A";

                if(message.equals("a")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = greatAnswers[rnd];
                }
                else if(message.equals("b")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = badAnswers[rnd] + correctOption;
                }
                else if(message.equals("c")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = badAnswers[rnd] + correctOption;
                }
            }
            else if(this.currentExercise == 2){
                String correctOption = "A";


                if(message.equals("a")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = greatAnswers[rnd];
                }
                else if(message.equals("b")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = badAnswers[rnd] + correctOption;
                }
                else if(message.equals("c")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = badAnswers[rnd] + correctOption;
                }
            }
            else if(this.currentExercise == 3){
                String correctOption = "B";

                if(message.equals("a")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = badAnswers[rnd] + correctOption;
                }
                else if(message.equals("b")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = greatAnswers[rnd];
                }
                else if(message.equals("c")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = badAnswers[rnd] + correctOption;
                }
            }
            else if(this.currentExercise == 4){
                String correctOption = "B";

                if(message.equals("a")){
                    int rnd = new Random().nextInt(badAnswers.length);

                    botMessage = badAnswers[rnd] + correctOption;
                }
                else if(message.equals("b")){
                    int rnd = new Random().nextInt(greatAnswers.length);

                    botMessage = greatAnswers[rnd];
                }

            }

            this.currentExercise = 0;
        }

        return botMessage;

    }


    public static String exercises(String botMessage, int currentExercise){

        if(currentExercise == 1){
            botMessage = "What does the following java code returns?\n " +
                    "'String a = \"java is cool\"" +
                    "System.out.println(\"hello world\");'\n" +
                    "(a) \"hello world\" \n" +
                    "(b) \"java is cool\"\n" +
                    "(c) \"System.out.println('hello world');\" ";

        }

        else if(currentExercise == 2){
            botMessage = "What does the '++' in a for loop do?\n" +
                    "(a) increments the value of a certain variable\n" +
                    "(b) does nothing\n" +
                    "(c) decrements the value of a certain variable";
        }

        else if(currentExercise == 3){
            botMessage = "Which option successfully uses a method named \"hello\" from a class called \"myClass\"?\n" +
                    "(a) hello(myClass)\n" +
                    "(b) myClass.hello()\n" +
                    "(c) hello.myClass()";
        }

        else if(currentExercise == 4){
            botMessage = "Is String a primitive type in Java?\n" +
                    "(a) yes\n" +
                    "(b) no";
        }

        return botMessage;
    }
}
