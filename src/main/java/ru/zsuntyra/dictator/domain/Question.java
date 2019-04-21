package ru.zsuntyra.dictator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {

    @Id
    @SequenceGenerator(name = "question_seq_gen", sequenceName = "question_id_seq")
    @GeneratedValue(generator = "question_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question")
    private List<Answer> answers;

}
