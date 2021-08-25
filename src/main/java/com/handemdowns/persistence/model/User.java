package com.handemdowns.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"email", "name"})
@ToString(exclude={"roles", "ads", "watchlist", "password"})
@Builder(builderMethodName = "objectBuilder")
@JsonIgnoreProperties({"roles", "ads", "watchlist", "password"})
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    private Long id;
	
	@Column(unique = true, name = "email", nullable = false)
    private String email;

	@Column(name = "name", nullable = false)
    private String name;
	
	@Column(name = "password", nullable = false)
    private String password;

	@Column(name = "enabled", nullable = false)
    private Boolean enabled;

	@Column(name = "token_expired")
    private Boolean tokenExpired;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Ad> ads;

    @ManyToMany
    @JoinTable(name = "user_watchlist", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "ad_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "ad_id"}))
    private Collection<Ad> watchlist;

	public static UserBuilder builder(String email, String name, String password) {
		return objectBuilder()
				.email(email)
				.name(name)
				.password(password)
				.enabled(true);
	}
}