package ru.zsuntyra.dictator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @SequenceGenerator(name = "answer_seq_gen", sequenceName = "answer_id_seq")
    @GeneratedValue(generator = "answer_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    private Question question;

    private String text;

    private int peopleImpact;

    private int armyImpact;

    private int policeImpact;

    private int humanRightDefendersImpact;

    private int powerImpact;

    private int respectImpact;

}
