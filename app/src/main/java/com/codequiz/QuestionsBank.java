package com.codequiz;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.codequiz.StartActivity;

import org.json.JSONObject;

public class QuestionsBank {

    private static List<QuestionsList> getData(String selectedTopicName) {
        ArrayList data = StartActivity.getList(selectedTopicName);
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(data);
        Type listUserType = new TypeToken<List<Question>>() {
        }.getType();
        ArrayList<Question> questions = gson.fromJson(jsonString, listUserType);
        ArrayList<QuestionsList> questionsLists = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            final QuestionsList question = new QuestionsList(questions.get(i).question, questions.get(i).options, questions.get(i).answer);
            questionsLists.add(question);
        }
        Collections.shuffle(questionsLists);
        return questionsLists;
    }

    public static List<QuestionsList> getQuestions(String selectedTopicName, String origin){
        List data = getData(selectedTopicName);
        if (origin.equals("selectedTopic")) return data.subList(0, 8);
        return data;
    }
}