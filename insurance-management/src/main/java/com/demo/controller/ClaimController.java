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

import com.demo.dto.ClaimDTO;
import com.demo.entity.Claim;
import com.demo.service.ClaimService;
import com.demo.util.Message;

@RestController
@RequestMapping(path = "/v1/api/claims")
public class ClaimController {
	
	@Autowired
	ClaimService claimService;
	
	@GetMapping(path ="/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Claim>> fetchAllClaims(){
		return ResponseEntity.ok(claimService.fetchAllClaim());
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<ClaimDTO> fetchClaimById(@PathVariable("id") Integer id){
		return ResponseEntity.ok(claimService.fetchClaimById(id));
	}
	
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> createClaim(@RequestBody ClaimDTO claimDTO){
		Claim claim = new Claim(claimDTO.getDescription(), claimDTO.getClaimDate(), claimDTO.getClaimStatus());
		return ResponseEntity.ok(claimService.createClaim(claim,claimDTO.getPolicyId()));
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> updateClaim(@PathVariable("id") Integer id,@RequestBody ClaimDTO claimDTO){
		Claim claim = new Claim(claimDTO.getDescription(), claimDTO.getClaimDate(), claimDTO.getClaimStatus());
		return ResponseEntity.ok(claimService.updateClaim(id,claim));
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Message> deleteClaim(@PathVariable("id") Integer id){
		return ResponseEntity.ok(claimService.deleteClaim(id));
	}
}
