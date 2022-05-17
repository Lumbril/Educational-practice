package com.example.questionnaire.repository;

import com.example.questionnaire.entity.UserAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswersRepository extends CrudRepository<UserAnswer, Long> {
    @Query("select ua from UserAnswer ua, User u where ua.user.id = u.id and ua.user.login = :login")
    List<UserAnswer> findByUserLogin(@Param("login") String login);
}
