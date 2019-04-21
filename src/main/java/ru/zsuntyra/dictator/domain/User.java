package ru.zsuntyra.dictator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usr")
public class User {

    @Id
    @SequenceGenerator(name = "usr_seq_gen", sequenceName = "usr_id_seq")
    @GeneratedValue(generator = "usr_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;

    private String password;

    private int progress;

    private Avatar activeAvatar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Rating rating;

    @ManyToMany
    @JoinTable(name = "usr_associate")
    private Set<Associate> associates;

    @ManyToMany
    @JoinTable(name = "usr_avatar")
    private Set<Avatar> avatars;

}
