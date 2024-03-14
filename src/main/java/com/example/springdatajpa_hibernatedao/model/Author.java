package com.example.springdatajpa_hibernatedao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Data
@NamedQuery(name = "author_find_all", query = "FROM Author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
}
