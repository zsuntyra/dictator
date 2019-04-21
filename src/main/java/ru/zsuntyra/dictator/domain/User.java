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
@Table(name = "usr")
public class User {

    @Id
    @SequenceGenerator(name = "usr_seq_gen", sequenceName = "usr_id_seq")
    @GeneratedValue(generator = "usr_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int progress;

    private Long activeAvatarId;

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
