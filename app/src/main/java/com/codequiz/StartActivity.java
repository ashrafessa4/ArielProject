package com.codequiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class StartActivity extends AppCompatActivity {

    private static ArrayList javaList = new ArrayList<>();
    private static ArrayList jsList = new ArrayList<>();
    private static ArrayList phpList = new ArrayList<>();
    private static ArrayList pyList = new ArrayList<>();
    private static ArrayList randomList = new ArrayList<>();
    // selectedTopicName will be assigned by a topic name ('java', 'javascript', 'python', 'php')
    private String selectedTopicName = "";
    private String TAG = "quizapp";
    private ProgressBar loader;
    private FirebaseFirestore firestore;
    private LinearLayout quizButtonsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);
        firestore = FirebaseFirestore.getInstance();

        // Initialize widgets from activity_splash_screen.xml
        final Button startBtn = findViewById(R.id.startQuizBtn);
        final Button practiceBtn = findViewById(R.id.startPractice);
        final Button randomQuizeBtn = findViewById(R.id.startRandomQuizBtn);
        final LinearLayout javaLayout = findViewById(R.id.javaLayout);
        final LinearLayout phpLayout = findViewById(R.id.phpLayout);
        final LinearLayout pyLayout = findViewById(R.id.pyLayout);
        final LinearLayout jsLayout = findViewById(R.id.jsLayout);
        loader = findViewById(R.id.idPBLoading);
        quizButtonsLayout = findViewById(R.id.quizBtnsLayout);

        javaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // assign java to selectedTopicName
                selectedTopicName = "java";
                if (javaList.isEmpty()) {
                    getQuestionsData();
                } else {
                    quizButtonsLayout.setVisibility(View.VISIBLE);
                }

                // select java layout
                javaLayout.setBackgroundResource(R.drawable.round_back_white_stroke10);

                // de-select other layouts
                phpLayout.setBackgroundResource(R.drawable.round_back_white10);
                pyLayout.setBackgroundResource(R.drawable.round_back_white10);
                jsLayout.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        phpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // assign php to selectedTopicName
                selectedTopicName = "php";
                if (phpList.isEmpty()) {
                    getQuestionsData();
                } else {
                    quizButtonsLayout.setVisibility(View.VISIBLE);
                }

                // select php layout
                phpLayout.setBackgroundResource(R.drawable.round_back_white_stroke10);

                // de-select other layouts
                javaLayout.setBackgroundResource(R.drawable.round_back_white10);
                pyLayout.setBackgroundResource(R.drawable.round_back_white10);
                jsLayout.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        pyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // assign html to selectedTopicName
                selectedTopicName = "python";
                if (pyList.isEmpty()) {
                    getQuestionsData();
                } else {
                    quizButtonsLayout.setVisibility(View.VISIBLE);
                }

                // select HTML layout
                pyLayout.setBackgroundResource(R.drawable.round_back_white_stroke10);

                // de-select other layouts
                javaLayout.setBackgroundResource(R.drawable.round_back_white10);
                phpLayout.setBackgroundResource(R.drawable.round_back_white10);
                jsLayout.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        jsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // assign android to selectedTopicName
                selectedTopicName = "js";
                if (jsList.isEmpty()) {
                    getQuestionsData();
                } else {
                    quizButtonsLayout.setVisibility(View.VISIBLE);
                }

                // select Android layout
                jsLayout.setBackgroundResource(R.drawable.round_back_white_stroke10);

                // de-select other layouts
                javaLayout.setBackgroundResource(R.drawable.round_back_white10);
                phpLayout.setBackgroundResource(R.drawable.round_back_white10);
                pyLayout.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        randomQuizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phpList.isEmpty() || javaList.isEmpty() || pyList.isEmpty() || jsList.isEmpty()) {
                    Toast.makeText(StartActivity.this, "You have to practice all topics first!", Toast.LENGTH_SHORT).show();
                } else {
//                    randomList.add(jsList);
                    randomList.addAll(javaList);
//                    randomList.add(pyList);
                    randomList.addAll(phpList);
                    // Create an Object of Intent to open quiz questions screen
                    final Intent intent = new Intent(StartActivity.this, MainActivity.class);

                    //put user entered name and selected topic name to intent for use in next activity
                    intent.putExtra("selectedTopic", "random");
                    intent.putExtra("origin", "selectedTopic");

                    // call startActivity to open next activity along with data(userName, selectedTopicName)
                    startActivity(intent);

                    finish(); // finish (destroy) this activity
                }
            }
        });

        practiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* if user not selected any topic yet then show a Toast message
                 * selectedTopicName will be empty or default value ("") if user not selected any topic yet*/
                if (selectedTopicName.isEmpty()) {
                    Toast.makeText(StartActivity.this, "Please select topic first", Toast.LENGTH_SHORT).show();
                } else {

                    // Create an Object of Intent to open quiz questions screen
                    final Intent intent = new Intent(StartActivity.this, MainActivity.class);

                    //put user entered name and selected topic name to intent for use in next activity
                    intent.putExtra("selectedTopic", selectedTopicName);
                    intent.putExtra("origin", "selectedTopicToPractice");

                    // call startActivity to open next activity along with data(userName, selectedTopicName)
                    startActivity(intent);

                    finish(); // finish (destroy) this activity
                }
            }
        });


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* if user not selected any topic yet then show a Toast message
                 * selectedTopicName will be empty or default value ("") if user not selected any topic yet*/

                if (javaList.isEmpty() && selectedTopicName.equals("java") || jsList.isEmpty() && selectedTopicName.equals("js")
                        || phpList.isEmpty() && selectedTopicName.equals("php") || pyList.isEmpty() && selectedTopicName.equals("python")) {
                    Toast.makeText(StartActivity.this, "Data is loading, try again!", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedTopicName.isEmpty()) {
                        Toast.makeText(StartActivity.this, "Please select topic first", Toast.LENGTH_SHORT).show();
                    } else {

                        // Create an Object of Intent to open quiz questions screen
                        final Intent intent = new Intent(StartActivity.this, MainActivity.class);

                        //put user entered name and selected topic name to intent for use in next activity
                        intent.putExtra("selectedTopic", selectedTopicName);
                        intent.putExtra("origin", "selectedTopic");

                        // call startActivity to open next activity along with data(userName, selectedTopicName)
                        startActivity(intent);

                        finish(); // finish (destroy) this activity
                    }
                }
            }
        });
    }

    private void getQuestionsData() {
        quizButtonsLayout.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        firestore.collection(selectedTopicName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                 if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference docRef = firestore.collection(selectedTopicName).document(document.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        switch (selectedTopicName) {
                                            case "java":
                                                javaList.add(document.getData());
                                                break;
                                            case "js":
                                                jsList.add(document.getData());
                                                break;
                                            case "php":
                                                phpList.add(document.getData());
                                                break;
                                            default:
                                                pyList.add(document.getData());
                                                break;
                                        }
                                        loader.setVisibility(View.GONE);
                                        quizButtonsLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(StartActivity.this, "Failed, please check your connection!", Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    Toast.makeText(StartActivity.this, "Failed, please check your connection!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(StartActivity.this, "Failed, please check your connection!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public static ArrayList getList(String list) {
        switch (list) {
            case "java":
                return javaList;
            case "php":
                return phpList;
            case "js":
                return jsList;
            case "python":
                return pyList;
            default:
                return randomList;
        }
    }
}