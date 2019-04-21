package ru.zsuntyra.dictator.domain;

import lombok.Data;
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

    private int peopleCoefficient;

    private int armyCoefficient;

    private int policeCoefficient;

    private int humanRightDefendersCoefficient;

    private int powerCoefficient;

    private int moneyCoefficient;

    private int cost;

    @ManyToMany(mappedBy = "associates")
    private Set<User> users;

}
