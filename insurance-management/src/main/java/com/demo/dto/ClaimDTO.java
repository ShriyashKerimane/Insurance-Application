package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDTO {

	private Integer claimNumber = null;
	private String description;
	private String claimDate;
	private String claimStatus;
	private Integer policyId = null;
	
}
