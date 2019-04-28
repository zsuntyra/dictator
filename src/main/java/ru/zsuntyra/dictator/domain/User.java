package ru.zsuntyra.dictator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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

    private int money;

    private Long activeAvatarId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "associate_id", referencedColumnName = "id")
    private Associate associate;

    @ManyToMany
    @JoinTable(name = "usr_associate")
    private Set<Associate> associates;

    @ManyToMany
    @JoinTable(name = "usr_avatar")
    private Set<Avatar> avatars;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.activeAvatarId = null;
        this.rating = new Rating();
        rating.setDate(new Date());
        rating.setUser(this);
        this.associates = new HashSet<>();
        this.avatars = new HashSet<>();
    }
}
