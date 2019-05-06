package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.*;
import ru.zsuntyra.dictator.repository.AssociateRepository;
import ru.zsuntyra.dictator.repository.QuestionRepository;
import ru.zsuntyra.dictator.repository.RatingRepository;
import ru.zsuntyra.dictator.repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Stateful
public class GameEJB {

    @Inject
    private QuestionRepository questionRepository;

    @Inject
    private RatingRepository ratingRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AssociateRepository associateRepository;
    @EJB
    private TokenEJB tokenEJB;

    @EJB
    private AuthEJB authEJB;

    private GameState gameState;

    public GameState startGame(Fraction userFraction, List<Associate> associates) {
        try {
            gameState = new GameState(userFraction, associates);
            return gameState;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

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

        if (gameState.getFractionCoefficients().values()
                .stream().anyMatch(coefficient -> coefficient <= -10)) {
            gameState.setDeadCounter(gameState.getDeadCounter() + 1);
        }

        return gameState;
    }

    public boolean isGameFinished() {
        if (gameState.getRespect() <= 0 || gameState.getDeadCounter() == 3) {
            updateRatingAfterGame();
            updateUserAfterGame();
            return true;
        } else {
            return false;
        }
    }

    private void updateUserAfterGame() {
        User user = authEJB.getAuthorizedUser();
        user.setMoney(user.getMoney() + gameState.getMoney());
        user.setProgress(user.getProgress() + gameState.getStepNumber());

        userRepository.update(user);
    }

    public List<Associate> getAllUserAssociate(){
        User user = authEJB.getAuthorizedUser();
        return associateRepository.findAllByUser(user);
    }
    private void updateRatingAfterGame() {
        User user = authEJB.getAuthorizedUser();
        Rating rating = ratingRepository.findByUserId(user.getId());

        if (rating.getProgress() < gameState.getStepNumber()) {
            rating.setProgress(gameState.getStepNumber());
            rating.setDate(new Date());
            ratingRepository.update(rating);
        }
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
            if (new Random().nextInt(100) < associate.getCoefficientByFraction(fraction) * 5) {
                relationship++;
            }
        }

        relationship = relationship > 15 ? 15 : relationship;
        relationship = relationship < -15 ? -15 : relationship;

        gameState.getFractionCoefficients().put(fraction, relationship);
    }

}
