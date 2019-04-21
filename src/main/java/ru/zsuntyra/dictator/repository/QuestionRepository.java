package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.domain.Question;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("questionRepository")
public class QuestionRepository extends JpaRepository<Question> {

    public QuestionRepository() {
        super(Question.class);
    }

}
