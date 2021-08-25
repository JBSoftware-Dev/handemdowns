package com.handemdowns.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "role", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@ToString(exclude={"users", "permissions"})
@Builder(builderMethodName = "objectBuilder")
@JsonIgnoreProperties({"users", "permissions"})
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(unique = true, name = "name", nullable = false)
	private String name;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private Collection<User> users;

	@ManyToMany
	@JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
	private Collection<Permission> permissions;

	public static RoleBuilder builder(String name) {
		return objectBuilder()
				.name(name);
	}
}