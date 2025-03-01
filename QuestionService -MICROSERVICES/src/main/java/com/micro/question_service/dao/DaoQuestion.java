package com.micro.question_service.dao;


import com.micro.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaoQuestion extends JpaRepository<Question,Integer> {
    List<Question> findByCategory(String category);
    @Query(value = "Select q.id from question q where q.category=:category Order By RANDOM() Limit :numQ ", nativeQuery = true)
    List<Integer> findQuestionByCategory(String category, int numQ);
}
