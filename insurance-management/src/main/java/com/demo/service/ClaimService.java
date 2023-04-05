package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.ClaimDTO;
import com.demo.entity.Claim;
import com.demo.entity.Policy;
import com.demo.exception.ClaimNotFoundException;
import com.demo.exception.PolicyNotFoundException;
import com.demo.repository.ClaimRepository;
import com.demo.repository.PolicyRepository;
import com.demo.util.Message;

@Service
public class ClaimService {

	@Autowired
	ClaimRepository claimRepository;
	
	@Autowired
	PolicyRepository policyRepository;

	private static final String EXCEPTION_MESSAGE = "Claim not found for id ";
	
	public List<Claim> fetchAllClaim() {
		return claimRepository.findAll();
	}

	public ClaimDTO fetchClaimById(Integer id) {
		Optional<Claim> claim = claimRepository.findById(id);
		if (claim.isPresent()) {
			Claim claimInfo = claim.get();
			return new ClaimDTO(id, claimInfo.getDescription(), claimInfo.getClaimDate(), claimInfo.getClaimStatus(), null);
		}else {
			throw new ClaimNotFoundException(EXCEPTION_MESSAGE+id);
		}
	}
	
	public Message createClaim(Claim claim, Integer policyId) {
		Optional<Policy> policy = policyRepository.findById(policyId);
		if(policy.isPresent()) {
			policy.get().getClaims().add(claim);
			policyRepository.save(policy.get());
			return new Message("New claim saved for policy id : "+policyId);
		}else {
			throw new PolicyNotFoundException("Policy not found for id "+policyId);
		}
	}
	
	public Message updateClaim(Integer id, Claim claim) {
		Optional<Claim> dbClaim = claimRepository.findById(id);
		if (dbClaim.isPresent()) {
			claim.setClaimNumber(id);
			claimRepository.save(claim);
			return new Message("Claim Updated");
		}else {
			throw new ClaimNotFoundException(EXCEPTION_MESSAGE+id);
		}
	}
	
	public Message deleteClaim(Integer id) {
		Optional<Claim> claim = claimRepository.findById(id);
		if(claim.isPresent()) {
			claimRepository.deleteById(id);
			return new Message("Claim with id "+id+" removed");
		}else {
			throw new ClaimNotFoundException(EXCEPTION_MESSAGE+id);
		}	
	}
	
}
