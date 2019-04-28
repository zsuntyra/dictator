package ru.zsuntyra.dictator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "associate")
public class Associate {

    @Id
    @SequenceGenerator(name = "associate_seq_gen", sequenceName = "associate_id_seq")
    @GeneratedValue(generator = "associate_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    /**
     * Every point in coefficients adds 5% to chance rising Fraction points
     */
    private int peopleCoefficient;

    private int armyCoefficient;

    private int policeCoefficient;

    private int humanRightDefendersCoefficient;

    private int powerCoefficient;

    private int moneyCoefficient;

    private int cost;

    @OneToOne(mappedBy = "associate")
    private User seller;

    @ManyToMany(mappedBy = "associates")
    private Set<User> users;

    public int getCoefficientByFraction(Fraction fraction) {
        switch (fraction) {
            case PEOPLE:
                return peopleCoefficient;
            case ARMY:
                return armyCoefficient;
            case POLICE:
                return policeCoefficient;
            case HUMAN_RIGHT_DEFENDERS:
                return humanRightDefendersCoefficient;
            case POWER:
                return powerCoefficient;
            default:
                return 0;
        }
    }

}
