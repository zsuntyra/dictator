package ru.zsuntyra.dictator.bean;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.config.Message;
import ru.zsuntyra.dictator.config.PathConfig;
import ru.zsuntyra.dictator.domain.*;
import ru.zsuntyra.dictator.ejb.GameEJB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ManagedBean(name = "gameBean", eager = true)
@SessionScoped
public class GameBean implements Serializable {

    @EJB
    private GameEJB gameEJB;

    @ManagedProperty("#{messageBean}")
    private MessageBean messageBean;

    private GameState gameState;

    private Question question;

    private Fraction userFraction;
    private List<Associate> associates = Collections.emptyList();

    public List<Associate> getAllAssciate(){
        return gameEJB.getAllUserAssociate();
    }

    public void addAssociate(Associate associate){
        associates.set(1,associate);
    }

    public boolean startGame() {
        if (userFraction != null) {
            gameState = gameEJB.startGame(userFraction, associates);
            if (gameState == null) {
                messageBean.setCurrentMessage(Message.GAME_WAS_NOT_STARTED);
                return false;
            }
            question = gameEJB.getNextQuestion();
            return true;
        } else {
            return false;
        }
    }
    public List<Map.Entry<Fraction, Integer>> getFractionCoefficients(){
        if (gameState != null) {
            return new ArrayList<>(gameState.getFractionCoefficients().entrySet());
        } else {
            return null;
        }
    }

    public boolean nextStep(Answer answer){
        // Answer answer = question.getAnswers().stream().filter(it -> it.getText().equals(text)).findFirst().get();
        gameState = gameEJB.nextStep(answer);
        question = gameEJB.getNextQuestion();

        if (gameEJB.isGameFinished()) {
            return true;
        } else return true;
    }
}



