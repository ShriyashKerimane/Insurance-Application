package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.PolicyDTO;
import com.demo.entity.Client;
import com.demo.entity.Policy;
import com.demo.exception.ClientNotFoundException;
import com.demo.exception.PolicyNotFoundException;
import com.demo.repository.ClientRepository;
import com.demo.repository.PolicyRepository;
import com.demo.util.Message;

@Service
public class PolicyService {

	@Autowired
	PolicyRepository policyRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	private static final String EXCEPTION_MESSAGE = "Policy not found for id ";

	public List<Policy> fetchAllPolicy() {
		return policyRepository.findAll();
	}

	public PolicyDTO fetchPolicyById(Integer id) {
		Optional<Policy> policy = policyRepository.findById(id);
		if (policy.isPresent()) {
			Policy policyInfo = policy.get();
			return new PolicyDTO(id, policyInfo.getType(), policyInfo.getCoverageAmount(), policyInfo.getPremium(),
					policyInfo.getStartDate(), policyInfo.getEndDate(), null, policyInfo.getClaims());
		} else {
			throw new PolicyNotFoundException(EXCEPTION_MESSAGE+id);
		}
	}

	public Message createPolicy(Policy policy, Integer clientId) {
		Optional<Client> client = clientRepository.findById(clientId);
		if(client.isPresent()) {
			client.get().getPolicies().add(policy);
			clientRepository.save(client.get());
			return new Message("New policy saved for client id : "+clientId);
		}else {
			throw new ClientNotFoundException("Client not found for id "+clientId);
		}
	}
	
	public Message updatePolicy(Integer id, Policy policy) {
		Optional<Policy> dbPolicy = policyRepository.findById(id);
		if(dbPolicy.isPresent()) {
			policy.setPolicyNumber(id);
			policyRepository.save(policy);
			return new Message("Policy Updated");
		}else {
			throw new PolicyNotFoundException(EXCEPTION_MESSAGE+id);
		}
	}

	public Message deletePolicy(Integer id) {
		Optional<Policy> policy = policyRepository.findById(id);
		if (policy.isPresent()) {
			policyRepository.deleteById(id);
			return new Message("Policy with id " + id + " removed");
		} else {
			throw new PolicyNotFoundException(EXCEPTION_MESSAGE+id);
		}
	}
}
