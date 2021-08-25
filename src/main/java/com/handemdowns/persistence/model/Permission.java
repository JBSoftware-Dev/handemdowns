package com.handemdowns.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "permission", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@ToString(exclude={"roles"})
@Builder(builderMethodName = "objectBuilder")
@JsonIgnoreProperties("roles")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    private Collection<Role> roles;

	public static PermissionBuilder builder(String name) {
		return objectBuilder()
				.name(name);
	}
}