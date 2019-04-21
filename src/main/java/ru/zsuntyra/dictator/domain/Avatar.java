package ru.zsuntyra.dictator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "avatar")
public class Avatar {

    @Id
    @SequenceGenerator(name = "avatar_seq_gen", sequenceName = "avatar_id_seq")
    @GeneratedValue(generator = "avatar_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String color;

    private String particle;

    private int cost;

    @ManyToMany(mappedBy = "avatars")
    private Set<User> users;

}
