package com.union.brainrush.service;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.union.brainrush.model.QuestionFormat;
@Component
public class QuestionService {
    private final ArrayList<QuestionFormat> qFormats = new ArrayList<>();

    public QuestionService() {
        loadQuestions();
    }

    private void loadQuestions() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/data.json")) {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<QuestionFormat> loadedUsers = mapper.readValue(inputStream, new TypeReference<ArrayList<QuestionFormat>>() {});
            qFormats.addAll(loadedUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<QuestionFormat> getQuestions() {
        return qFormats;
    }

    public void addQuestion(QuestionFormat qFormat) {
    	qFormats.add(qFormat);
    }
}
