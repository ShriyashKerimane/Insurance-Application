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
public class Policy {

	@Id
	@SequenceGenerator(sequenceName = "policy_sequence",initialValue = 1,allocationSize = 1, name = "policy_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "policy_sequence")
	private Integer policyNumber;
	private String type;
	private Double coverageAmount;
	private Double premium;
	private String startDate;
	private String endDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "policyId")
	private List<Claim> claims = new ArrayList<>();
	
	public Policy(String type, Double coverageAmount, Double premium, String startDate, String endDate){
		super();
		this.type = type;
		this.coverageAmount = coverageAmount;
		this.premium = premium;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}
