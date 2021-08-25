package com.handemdowns.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persistent_logins", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"username", "series", "token"})
@ToString
public class PersistentLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "series", nullable = false)
    private String series;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Date lastUsed;
}