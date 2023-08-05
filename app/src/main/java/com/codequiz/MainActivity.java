package com.codequiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    // total quiz time in minutes
    private int totalTimeInMins = 1;

    // Timer class object for countdown timer
    private Timer quizTimer;
    private int seconds = 0; // current countdown seconds

    // questions array list
    private List<QuestionsList> questionsLists;

    // Current questions index position from  questionsLists ArrayList.
    private int currentQuestionPosition = 0;

    // Options
    private AppCompatButton option1, option2, option3, option4;

    // next button
    private AppCompatButton nextBtn;

    // Total questions and main question TextView
    private TextView question;
    private TextView questions;
    private String origin;

    // selectedOption's Value. if user not selected any option yet then it is empty by default
    private String selectedOptionByUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        // initialize widgets from activity_main.xml file
        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView topicName = findViewById(R.id.topicName);
        final TextView timer = findViewById(R.id.timer);
        final ImageView timerIcon = findViewById(R.id.timerIcon);
        final TextView learn = findViewById(R.id.learnMore);
        final ImageView learnIcon = findViewById(R.id.learnMoreIcon);
        final LinearLayout topRightLayout = findViewById(R.id.topRightLayout);

        question = findViewById(R.id.question);
        questions = findViewById(R.id.questions);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextBtn = findViewById(R.id.nextButton);

        // get Topic Name and User Name from StartActivity via Intent
        final String getTopicName = getIntent().getStringExtra("selectedTopic");
        origin = getIntent().getStringExtra("origin");

        if (origin.equals("selectedTopicToPractice")) {
            learn.setVisibility(View.VISIBLE);
            learnIcon.setVisibility((View.VISIBLE));
        } else {
            timer.setVisibility(View.VISIBLE);
            timerIcon.setVisibility(View.VISIBLE);
            // start quiz countdown timer
            startTimer(timer);
        }

        // set Topic Name to TextView
        topicName.setText(getTopicName.toUpperCase());

        // get questions from QuestionsBank class according to selectedTopicName and assign to questionsLists ArrayList
        questionsLists = QuestionsBank.getQuestions(getTopicName, origin);

        // set current question to TextView along with options from questionsLists ArrayList
        questions.setText((currentQuestionPosition + 1) + "/" + questionsLists.size());
        question.setText(questionsLists.get(currentQuestionPosition).getQuestion());
        ArrayList options = questionsLists.get(currentQuestionPosition).getOptions();
        option1.setText(options.get(0).toString());
        option2.setText(options.get(1).toString());
        option3.setText(options.get(2).toString());
        option4.setText(options.get(3).toString());

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (origin.equals("selectedTopicToPractice")) {
                    // check if user has not attempted this question yet
                    if (selectedOptionByUser.isEmpty()) {
                        selectedOptionByUser = option1.getText().toString();
                        // change selected AppCompatButton background color and text color
                        option1.setBackgroundResource(R.drawable.round_back_red10);
                        option1.setTextColor(Color.WHITE);
                        // reveal answer
                        revealAnswer();
                        // assign user answer value to userSelectedOption in QuestionsList class
                        questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                    }
                } else {
                    // check if user has not attempted this question yet
                    selectedOptionByUser = option1.getText().toString();
                    handleOptionSelection("Option1");
                    // change selected AppCompatButton background color and text color
                    option1.setTextColor(Color.WHITE);
                    // change selected AppCompatButton background color and text color
                    option1.setBackgroundResource(R.drawable.round_back_purple10);
                    // assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (origin.equals("selectedTopicToPractice")) {
                    // check if user has not attempted this question yet
                    if (selectedOptionByUser.isEmpty()) {
                        selectedOptionByUser = option2.getText().toString();
                        // change selected AppCompatButton background color and text color
                        option2.setBackgroundResource(R.drawable.round_back_red10);
                        option2.setTextColor(Color.WHITE);
                        // reveal answer
                        revealAnswer();
                        // assign user answer value to userSelectedOption in QuestionsList class
                        questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                    }
                } else {
                    // check if user has not attempted this question yet
                    selectedOptionByUser = option2.getText().toString();
                    handleOptionSelection("Option2");
                    // change selected AppCompatButton background color and text color
                    option2.setTextColor(Color.WHITE);
                    // change selected AppCompatButton background color and text color
                    option2.setBackgroundResource(R.drawable.round_back_purple10);
                    // assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (origin.equals("selectedTopicToPractice")) {
                    // check if user has not attempted this question yet
                    if (selectedOptionByUser.isEmpty()) {
                        selectedOptionByUser = option3.getText().toString();
                        // change selected AppCompatButton background color and text color
                        option3.setBackgroundResource(R.drawable.round_back_red10);
                        option3.setTextColor(Color.WHITE);
                        // reveal answer
                        revealAnswer();
                        // assign user answer value to userSelectedOption in QuestionsList class
                        questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                    }
                } else {
                    // check if user has not attempted this question yet
                    selectedOptionByUser = option3.getText().toString();
                    handleOptionSelection("Option3");
                    // change selected AppCompatButton background color and text color
                    option3.setTextColor(Color.WHITE);
                    // change selected AppCompatButton background color and text color
                    option3.setBackgroundResource(R.drawable.round_back_purple10);
                    // assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (origin.equals("selectedTopicToPractice")) {
                    // check if user has not attempted this question yet
                    if (selectedOptionByUser.isEmpty()) {
                        selectedOptionByUser = option4.getText().toString();
                        // change selected AppCompatButton background color and text color
                        option4.setBackgroundResource(R.drawable.round_back_red10);
                        option4.setTextColor(Color.WHITE);
                        // reveal answer
                        revealAnswer();
                        // assign user answer value to userSelectedOption in QuestionsList class
                        questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                    }
                } else {
                    // check if user has not attempted this question yet
                    selectedOptionByUser = option4.getText().toString();
                    handleOptionSelection("Option4");
                    // change selected AppCompatButton background color and text color
                    option4.setTextColor(Color.WHITE);
                    // change selected AppCompatButton background color and text color
                    option4.setBackgroundResource(R.drawable.round_back_purple10);
                    // assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                }
            }

        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // cancel timer
                    quizTimer.purge();
                    quizTimer.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (origin.equals("selectedTopicToPractice")) {
                    if (currentQuestionPosition == 0) {
                        startActivity(new Intent(MainActivity.this, StartActivity.class));
                        finish(); // finish(destroy) this activity
                    } else {
                        changeNextQuestion(false);
                    }
                } else {
                    // open StartActivity.java
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                    finish(); // finish(destroy) this activity
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if user has not selected any option yet
                if (selectedOptionByUser.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    changeNextQuestion(true);
                }
            }
        });

        topRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=learn " + getTopicName));
                startActivity(browserIntent);
            }
        });
    }

    private void handleOptionSelection(String selectedOptionByUser) {
        switch (selectedOptionByUser) {
            case "Option1":
                option2.setTextColor(Color.parseColor("#1F6BB8"));
                option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                option3.setTextColor(Color.parseColor("#1F6BB8"));
                option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                option4.setTextColor(Color.parseColor("#1F6BB8"));
                option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                break;
            case "Option2":
                option1.setTextColor(Color.parseColor("#1F6BB8"));
                option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                option3.setTextColor(Color.parseColor("#1F6BB8"));
                option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                option4.setTextColor(Color.parseColor("#1F6BB8"));
                option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                break;
            case "Option3":
                option1.setTextColor(Color.parseColor("#1F6BB8"));
                option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                option2.setTextColor(Color.parseColor("#1F6BB8"));
                option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                option4.setTextColor(Color.parseColor("#1F6BB8"));
                option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                break;
            case "Option4":
                option1.setTextColor(Color.parseColor("#1F6BB8"));
                option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                option2.setTextColor(Color.parseColor("#1F6BB8"));
                option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                option3.setTextColor(Color.parseColor("#1F6BB8"));
                option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
                break;
        }

    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    private void startTimer(TextView timerTextView) {

        quizTimer = new Timer();
        quizTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (seconds == 0 && totalTimeInMins == 0) {
                            // cancel timer
                            quizTimer.purge();
                            quizTimer.cancel();
                            Toast.makeText(MainActivity.this, "Timer Over", Toast.LENGTH_SHORT).show();
                            revealAnswer();
                            setTimeout(() -> changeNextQuestion(true), 2500);
                        } else if (seconds == 0) {
                            totalTimeInMins--;
                            seconds = 59;
                        } else {
                            seconds--;
                        }

                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        // check if minutes has only one digit(Ex. 9) then attach 0 before the digit to make it 09
                        if (finalMinutes.length() == 1) {
                            finalMinutes = "0" + finalMinutes;
                        }

                        // check if seconds has only one digit(Ex. 9) then attach 0 before the digit to make it 09
                        if (finalSeconds.length() == 1) {
                            finalSeconds = "0" + finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + ":" + finalSeconds);
                    }
                });
            }
        }, 1000, 1000);
    }

    private void revealAnswer() {

        // get answer of current question
        final String getAnswer = questionsLists.get(currentQuestionPosition).getAnswer();

        // change background color and text color of option which match with answer
        if (option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(R.drawable.round_back_green10);
            option1.setTextColor(Color.WHITE);
        } else if (option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.round_back_green10);
            option2.setTextColor(Color.WHITE);
        } else if (option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.round_back_green10);
            option3.setTextColor(Color.WHITE);
        } else if (option4.getText().toString().equals(getAnswer)) {
            option4.setBackgroundResource(R.drawable.round_back_green10);
            option4.setTextColor(Color.WHITE);
        }
    }

    private void changeNextQuestion(Boolean isNextClick) {
        try {
            // cancel timer
            quizTimer.purge();
            quizTimer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final TextView timer = findViewById(R.id.timer);
        seconds = 0;
        totalTimeInMins++;
        startTimer(timer);
        if (isNextClick) {
            // increment currentQuestionPosition by 1 for next question
            currentQuestionPosition++;
        } else {
            currentQuestionPosition--;
        }


        // change next button text to submit if it is last question
        if ((currentQuestionPosition + 1) == questionsLists.size()) {
            if (origin.equals("selectedTopicToPractice")) {
                nextBtn.setText("Finish");
            } else {
                nextBtn.setText("Submit Quiz");
            }
        }

        // check if next question is available. else quiz completed
        if (currentQuestionPosition < questionsLists.size()) {

            // make selectedOptionByUser empty to hold next question's answer
            selectedOptionByUser = "";

            // set normal background color and text color to options
            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));
            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));
            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));
            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            // set current question to TextView along with options from questionsLists ArrayList
            questions.setText((currentQuestionPosition + 1) + "/" + questionsLists.size());
            question.setText(questionsLists.get(currentQuestionPosition).getQuestion());
            ArrayList options = questionsLists.get(currentQuestionPosition).getOptions();
            option1.setText(options.get(0).toString());
            option2.setText(options.get(1).toString());
            option3.setText(options.get(2).toString());
            option4.setText(options.get(3).toString());
        } else {
            try {
                // cancel timer
                quizTimer.purge();
                quizTimer.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Open result activity along with correct and incorrect answers
            Intent intent = new Intent();
            if (origin.equals("selectedTopicToPractice")) {
                intent = new Intent(MainActivity.this, StartActivity.class);
            } else {
                intent = new Intent(MainActivity.this, QuizResults.class);
                intent.putExtra("correct", getCorrectAnswers());
                intent.putExtra("incorrect", getIncorrectAnswers());
            }

            startActivity(intent);

            // finish(destroy) this activity
            finish();
        }
    }

    private int getCorrectAnswers() {

        int correctAnswers = 0;

        for (int i = 0; i < questionsLists.size(); i++) {
            final String getUserSelectedOption = questionsLists.get(i).getUserSelectedOption();
            final String getAnswer = questionsLists.get(i).getAnswer();

            // compare user selected option with original answer
            if (getUserSelectedOption.equals(getAnswer)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private int getIncorrectAnswers() {

        int incorrectAnswers = 0;

        for (int i = 0; i < questionsLists.size(); i++) {
            final String getUserSelectedOption = questionsLists.get(i).getUserSelectedOption();
            final String getAnswer = questionsLists.get(i).getAnswer();

            // compare user selected option with original answer
            if (!getUserSelectedOption.equals(getAnswer)) {
                incorrectAnswers++;
            }
        }
        return incorrectAnswers;
    }

    @Override
    public void onBackPressed() {
        // cancel timer
        try {
            quizTimer.purge();
            quizTimer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // open StartActivity.java
        startActivity(new Intent(MainActivity.this, StartActivity.class));
        finish(); // finish(destroy) this activity
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}