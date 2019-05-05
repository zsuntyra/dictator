package ru.zsuntyra.dictator.bean;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.config.Message;
import ru.zsuntyra.dictator.config.PathConfig;
import ru.zsuntyra.dictator.domain.*;
import ru.zsuntyra.dictator.ejb.RatingEJB;

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
@ManagedBean(name = "ratingBean", eager = true)
@SessionScoped
public class RatingBean implements Serializable {

    @EJB
    private RatingEJB ratingEJB;

    @ManagedProperty("#{messageBean}")
    private MessageBean messageBean;


    public List<Rating> getTopOfRating(){
            return ratingEJB.getTop50PlayersByRating();
    }

//    public boolean startGame() {
//        if (userFraction != null) {
//            gameState = gameEJB.startGame(userFraction, associates);
//            if (gameState == null) {
//                messageBean.setCurrentMessage(Message.GAME_WAS_NOT_STARTED);
//                return false;
//            }
//            question = gameEJB.getNextQuestion();
//            return true;
//        } else {
//            return false;
//        }
//    }
//    public List<Map.Entry<Fraction, Integer>> getFractionCoefficients(){
//        if (gameState != null) {
//            return new ArrayList<>(gameState.getFractionCoefficients().entrySet());
//        } else {
//            return null;
//        }
//    }
//
//    public boolean nextStep(Answer answer){
//        // Answer answer = question.getAnswers().stream().filter(it -> it.getText().equals(text)).findFirst().get();
//        gameState = gameEJB.nextStep(answer);
//
//        if (gameEJB.isGameFinished()) {
//            return true;
//        } else return true;
//    }
}
