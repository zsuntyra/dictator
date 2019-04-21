package ru.zsuntyra.dictator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @SequenceGenerator(name = "rating_seq_gen", sequenceName = "rating_id_seq")
    @GeneratedValue(generator = "rating_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(mappedBy = "rating")
    private User user;

    private int progress;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date;

}
