package ru.zsuntyra.dictator.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GameState {

    private Fraction userFraction;

    private Map<Fraction, Integer> fractionCoefficients;

    private List<Associate> associates;

    private int money;

    private int respect;

    private int stepNumber;

    private int deadCounter;

    public GameState(Fraction userFraction, List<Associate> associates) {
        this.userFraction = userFraction;
        this.associates = new ArrayList<>(associates);
        this.respect = 100;

        this.fractionCoefficients = new HashMap<>();
        for (Fraction f : Fraction.values()) {
            this.fractionCoefficients.put(f, 5); // 5 is default Fraction coefficient
        }
    }



}
