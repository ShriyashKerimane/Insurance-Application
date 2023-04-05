package com.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Claim {

	@Id
	@SequenceGenerator(sequenceName = "claim_sequence",initialValue = 1,allocationSize = 1, name = "claim_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "claim_sequence")
	private Integer claimNumber;
	private String description;
	private String claimDate;
	private String claimStatus;
	
	public Claim(String description, String claimDate, String claimStatus) {
		super();
		this.description = description;
		this.claimDate = claimDate;
		this.claimStatus = claimStatus;

	}
		
}
