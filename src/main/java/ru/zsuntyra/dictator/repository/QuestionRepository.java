package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.domain.Question;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.Random;

@Singleton
@Named("questionRepository")
public class QuestionRepository extends JpaRepository<Question> {

    public QuestionRepository() {
        super(Question.class);
    }

    public Question getRandomQuestion() {
        Query countQuery = getEntityManager().createNativeQuery("SELECT count(*) FROM Question");
        BigInteger count = (BigInteger) countQuery.getSingleResult();

        Random random = new Random();
        int number = random.nextInt(count.intValue());

        TypedQuery<Question> selectQuery = getEntityManager()
                .createQuery("SELECT q FROM Question q", Question.class);
        selectQuery.setFirstResult(number);
        selectQuery.setMaxResults(1);
        return getElementOrNull(selectQuery.getResultList());
    }

}
