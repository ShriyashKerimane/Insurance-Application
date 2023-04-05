package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.PolicyDTO;
import com.demo.entity.Policy;
import com.demo.service.PolicyService;
import com.demo.util.Message;

@RestController
@RequestMapping(path = "/v1/api/policies")
public class PolicyController {

	@Autowired
	PolicyService policyService;

	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Policy>> fetchAllPolicies() {
		return ResponseEntity.ok(policyService.fetchAllPolicy());
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PolicyDTO> fetchPolicyById(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(policyService.fetchPolicyById(id));
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> createPolicy(@RequestBody PolicyDTO policyDTO) {
		Policy policy = new Policy(policyDTO.getType(), policyDTO.getCoverageAmount(), policyDTO.getPremium(),
				policyDTO.getStartDate(), policyDTO.getEndDate());
		return ResponseEntity.ok(policyService.createPolicy(policy,policyDTO.getClientId()));
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> updatePolicy(@PathVariable("id")  Integer id,@RequestBody PolicyDTO policyDTO) {
		Policy policy = new Policy(policyDTO.getType(), policyDTO.getCoverageAmount(), policyDTO.getPremium(),
				policyDTO.getStartDate(), policyDTO.getEndDate());
		return ResponseEntity.ok(policyService.updatePolicy(id, policy));
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deletePolicy(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(policyService.deletePolicy(id));
	}

}
