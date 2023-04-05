package com.demo.dto;

import java.util.List;

import com.demo.entity.Claim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDTO {

	private Integer policyNumber = null;
	private String type;
	private Double coverageAmount;
	private Double premium;
	private String startDate;
	private String endDate;
	private Integer clientId = null;
	private List<Claim> claims = null;
	
}
