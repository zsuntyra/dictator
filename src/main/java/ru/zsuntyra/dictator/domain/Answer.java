package ru.zsuntyra.dictator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
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
