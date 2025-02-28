package com.example.demo.service;

import com.example.demo.dao.DaoQuestion;
import com.example.demo.dao.QuizDao;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Quiz;
import com.example.demo.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    DaoQuestion daoQuestion;


    public ResponseEntity<String> createQuiz(String category,int numQ,String title) {
        try {
            List<Question> questions = daoQuestion.findQuestionByCategory(category, numQ);
            Quiz quiz = new Quiz();

            quiz.setQuestions(questions);
            quiz.setTitle(title);

            quizDao.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        try {
            List<Question> questionFromDb = quiz.get().getQuestions();
            List<QuestionWrapper> questionsToUser = new ArrayList<>();

            for (Question q : questionFromDb) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsToUser.add(qw);
            }
            return new ResponseEntity<>(questionsToUser, HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);


    }

    public ResponseEntity<Integer> getResults(int id, List<Response> responses) {
            Quiz quiz=quizDao.findById(id).get();
            List<Question> questions = quiz.getQuestions();
            int result=0,i=0;
            for(Response r:responses){
                if(r.getResponse().equals(questions.get(i).getRightAnswer())){
                    result++;
                }
                i++;
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
