package ru.zsuntyra.dictator.ejb;

import lombok.Getter;
import lombok.Setter;
import ru.zsuntyra.dictator.domain.GameState;
import ru.zsuntyra.dictator.domain.Question;

import javax.ejb.Stateful;

@Getter
@Setter
@Stateful
public class GameEJB {

    private GameState gameState;

//    public Question getNextQuestion() {
//        
//    }

}
