package com.example.springdatajpa_hibernatedao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Data
@NamedQueries({
	@NamedQuery(name = "author_find_all", query = "FROM Author"),
	@NamedQuery(name = "author_find_by_name", query = "FROM Author a WHERE a.firstName = :first_name and a.lastName = :last_name")
})
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
}
