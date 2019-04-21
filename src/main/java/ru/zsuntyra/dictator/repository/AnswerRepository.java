package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.domain.Answer;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Named("answerRepository")
public class AnswerRepository extends JpaRepository<Answer> {

    public AnswerRepository() {
        super(Answer.class);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        String query = "SELECT a FROM Answer a WHERE a.question.id=" + questionId;
        return getEntityManager().createQuery(query, Answer.class).getResultList();
    }

}
