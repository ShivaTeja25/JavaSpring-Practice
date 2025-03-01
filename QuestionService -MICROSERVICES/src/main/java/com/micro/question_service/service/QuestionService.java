package com.micro.question_service.service;


import com.micro.question_service.dao.DaoQuestion;
import com.micro.question_service.model.Question;
import com.micro.question_service.model.QuestionWrapper;
import com.micro.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    DaoQuestion daoQuestion;

    public ResponseEntity<List<Question>> getAllQuestions() {
         try {
             return new ResponseEntity<>(daoQuestion.findAll(), HttpStatus.OK);
         }
         catch(Exception e){
             e.printStackTrace();
         }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Question>> getByCategory(String category) {
        try {
            return new ResponseEntity<>(daoQuestion.findByCategory(category), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestions(Question question) {
        try{
            daoQuestion.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }catch( Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
            try{
                List<Integer> questions = daoQuestion.findQuestionByCategory(categoryName, numQuestions);
                return new ResponseEntity<>(questions, HttpStatus.OK);
            }catch(Exception e){
                e.printStackTrace();
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> questions) {

        try {
            List<QuestionWrapper> wrappers = new ArrayList<>();
            List<Question> quest = new ArrayList<>();

            for (Integer i : questions) {
                quest.add(daoQuestion.findById(i).get()); //returns Optional so use get()
            }
            for (Question q : quest) {
                QuestionWrapper qw = new QuestionWrapper();
                qw.setId(q.getId());
                qw.setQuestionTitle(q.getQuestionTitle());
                qw.setOption1(q.getOption1());
                qw.setOption2(q.getOption2());
                qw.setOption3(q.getOption3());
                qw.setOption4(q.getOption4());
                wrappers.add(qw);
            }
            return new ResponseEntity<>(wrappers, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        try {
            int result = 0;
            for (Response r : responses) {
                Question q = new Question();
                q = daoQuestion.findById(r.getId()).get();
                if (r.getResponse().equals(q.getRightAnswer())) {
                    result++;
                }
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
    }
}
