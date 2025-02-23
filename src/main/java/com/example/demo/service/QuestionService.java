package com.example.demo.service;

import com.example.demo.dao.DaoQuestion;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
}
