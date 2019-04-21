package ru.zsuntyra.dictator.repository;

import org.junit.Test;
import ru.zsuntyra.dictator.domain.Answer;
import ru.zsuntyra.dictator.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionRepositoryTest {

    @Test
    public void saveWithAnswersAndFindTest() {
        QuestionRepository questionRepository = new QuestionRepository();

        String questionText = "questionTestTest";
        String ans1Text = "ans1TestText";
        String ans2Text = "ans2TestText";

        Answer ans1 = new Answer();
        ans1.setText(ans1Text);
        Answer ans2 = new Answer();
        ans2.setText(ans2Text);

        Question question = new Question();
        question.setText(questionText);
        question.setAnswers(new ArrayList<>());
        question.getAnswers().add(ans1);
        question.getAnswers().add(ans2);

        ans1.setQuestion(question);
        ans2.setQuestion(question);

        questionRepository.create(question);

        AnswerRepository answerRepository = new AnswerRepository();
        List<Answer> receivedList = answerRepository.findAllByQuestionId(question.getId());

        assertEquals(2, receivedList.size());
        assertEquals(ans1.getText(), receivedList.get(0).getText());
        assertEquals(ans2.getText(), receivedList.get(1).getText());
        assertEquals(question.getId(), receivedList.get(0).getQuestion().getId());
    }

}
