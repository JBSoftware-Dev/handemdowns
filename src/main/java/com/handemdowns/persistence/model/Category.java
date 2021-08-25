package com.handemdowns.persistence.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"code", "name"})
@ToString
@Builder(builderMethodName = "objectBuilder")
public class Category {
	@Id
	@Column(unique = true, name = "code", nullable = false)
    private String code;
	
	@Column(unique = true, name = "name", nullable = false)
    private String name;

	public static CategoryBuilder builder(String code, String name) {
		return objectBuilder()
				.code(code)
				.name(name);
	}
}