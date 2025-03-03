package com.micro.quiz_service.service;


import com.micro.quiz_service.dao.QuizDao;
import com.micro.quiz_service.feign.QuizInterface;
import com.micro.quiz_service.model.QuestionWrapper;
import com.micro.quiz_service.model.Quiz;
import com.micro.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;




    public ResponseEntity<String> createQuiz(String category,int numQ,String title) {
        try {
            List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody() ;
            Quiz quiz = new Quiz();

            quiz.setQuestionIds(questions);
            quiz.setTitle(title);

            quizDao.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz=quizDao.findById(id).get();
        List<Integer> questionsToUser=quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> ques=quizInterface.getQuestionsById(questionsToUser);

            return  ques;
        }




    public ResponseEntity<Integer> getResults(int id, List<Response> responses) {

        return quizInterface.getScore(responses);
    }
}
