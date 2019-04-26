package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.*;
import ru.zsuntyra.dictator.repository.QuestionRepository;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.Random;

@Getter
@Setter
@Stateful
public class GameEJB {

    @Inject
    private QuestionRepository questionRepository;

    private GameState gameState;

    public Question getNextQuestion() {
        return questionRepository.getRandomQuestion();
    }

    public GameState nextStep(Answer answer) {
        gameState.setStepNumber(gameState.getStepNumber() + 1);

        updateCoefficient(Fraction.ARMY, answer.getArmyImpact());
        updateCoefficient(Fraction.HUMAN_RIGHT_DEFENDERS, answer.getHumanRightDefendersImpact());
        updateCoefficient(Fraction.PEOPLE, answer.getPeopleImpact());
        updateCoefficient(Fraction.POLICE, answer.getPoliceImpact());
        updateCoefficient(Fraction.POWER, answer.getPowerImpact());

        updateRespect(answer.getRespectImpact());
        updateMoney();
        // TODO add calling end game checker

        return gameState;
    }

    private void updateRespect(int impact) {
        int respect = gameState.getRespect() + impact;
        if (gameState.getStepNumber() % 10 == 0 && new Random().nextInt(100) < gameState.getStepNumber() / 2.0) {
            respect--;
        }

        respect = respect > 100 ? 100 : respect;
        gameState.setRespect(respect);
    }

    private void updateMoney() {
        int money = gameState.getMoney();

        money = Math.random() > 0.5 ? money + 1 : money;

        for (Associate associate : gameState.getAssociates()) {
            if (new Random().nextInt(100) < associate.getMoneyCoefficient() * 5.0) {
                money++;
            }
        }

        gameState.setMoney(money);
    }

    private void updateCoefficient(Fraction fraction, int impact) {
        int relationship = gameState.getFractionCoefficients().get(fraction) + impact;

        for (Associate associate : gameState.getAssociates()) {
            if (new Random().nextInt(100) < associate.getCoefficientByFranction(fraction) * 5) {
                relationship++;
            }
        }

        relationship = relationship > 15 ? 15 : relationship;
        relationship = relationship < -15 ? -15 : relationship;

        gameState.getFractionCoefficients().put(fraction, relationship);

    }

}
