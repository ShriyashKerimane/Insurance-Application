package com.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
	
	@Id
	@SequenceGenerator(sequenceName = "client_sequence", initialValue = 1, allocationSize = 1, name = "client_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
	private Integer id;
	private String name;
	private String dateOfBirth;
	private String address;
	private String email;
	private String mobile;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "clientId")
	private List<Policy> policies = new ArrayList<>();


	public Client(String name, String dateOfBirth, String address, String email, String mobile) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.email = email;
		this.mobile = mobile;
	}
	
}
