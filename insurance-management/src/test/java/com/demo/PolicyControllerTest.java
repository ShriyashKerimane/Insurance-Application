package com.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.dto.PolicyDTO;
import com.demo.entity.Client;
import com.demo.entity.Policy;
import com.demo.repository.ClientRepository;
import com.demo.repository.PolicyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PolicyControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	PolicyRepository policyRepository;
	
	@MockBean
	ClientRepository clientRepository;
	
	@Test
	void testFetchAllPolicies() throws Exception{
		List<Policy> list = new ArrayList<>();
		list.add(new Policy(1, "life", 100000.0, 1000.0, "2023-01-01", "2025-01-01",null));
		when(policyRepository.findAll()).thenReturn(list);
		mvc.perform(get("/v1/api/policies/")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json("[{'policyNumber' : 1, 'type' : 'life', 'coverageAmount' : 100000.0, 'premium' : 1000.0, 'startDate' : '2023-01-01', 'endDate' : '2025-01-01', 'claims' : null}]"));
	}
	
	@Test
	void testFetchPolicyByIdPass() throws Exception{
		Policy policy = new Policy(1, "life", 100000.0, 1000.0, "2023-01-01", "2025-01-01", null);
		when(policyRepository.findById(1)).thenReturn(Optional.of(policy));
		mvc.perform(get("/v1/api/policies/1")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'policyNumber' : 1, 'type' : 'life', 'coverageAmount' : 100000.0, 'premium' : 1000.0, 'startDate' : '2023-01-01', 'endDate' : '2025-01-01', 'claims' : null}"));	
	}
	
	@Test
	void testFetchPolicyByIdFail() throws Exception{
		when(policyRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(get("/v1/api/policies/1")).andDo(print()).andExpect(status().is4xxClientError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'statusCode' : 404, 'message': 'Policy not found for id 1'}"));	
	}
	
	@Test
	void testCreatePolicyPass() throws Exception{
		PolicyDTO policyDTO = new PolicyDTO(1, "life", 100000.0, 1000.0, "2023-01-01", "2025-01-01", 1, null);
		Client client = new Client("abc", "2020-01-01", "xyz", "abc@nowhere.com", "1234567890");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(policyDTO);
		
		when(clientRepository.findById(1)).thenReturn(Optional.of(client));
		mvc.perform(post("/v1/api/policies/").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
			.andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("New policy saved for client id : 1"));
	}
	
	@Test
	void testCreatePolicyFail() throws Exception{
		PolicyDTO policyDTO = new PolicyDTO(1, "life", 100000.0, 1000.0, "2023-01-01", "2025-01-01", 1, null);		
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(policyDTO);
		
		when(clientRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(post("/v1/api/policies/").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
			.andDo(print()).andExpect(status().is4xxClientError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'statusCode' : 404, 'message': 'Client not found for id 1'}"));
	}
	
	@Test
	void testUpdatePolicyPass() throws Exception{
		Policy policy = new Policy(1, "life", 100000.0, 1000.0, "2023-01-01", "2025-01-01",null);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(policy);
		
		when(policyRepository.findById(1)).thenReturn(Optional.of(policy));
		mvc.perform(put("/v1/api/policies/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
			.andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Policy Updated"));;
	}
	
	@Test
	void testUpdatePolicyFail() throws Exception{
		Policy policy = new Policy(1, "life", 100000.0, 1000.0, "2023-01-01", "2025-01-01", null);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(policy);
		
		when(policyRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(put("/v1/api/policies/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
			.andDo(print()).andExpect(status().is4xxClientError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'statusCode' : 404, 'message': 'Policy not found for id 1'}"));
	}
	
	@Test
	void testDeletePolicyPass() throws Exception{
		Policy policy = new Policy(1, "life", 100000.0, 1000.0, "2023-01-01", "2025-01-01", null);
		when(policyRepository.findById(1)).thenReturn(Optional.of(policy));
		mvc.perform(delete("/v1/api/policies/1")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Policy with id 1 removed"));
	}
	
	@Test
	void testDeletePolicyFail() throws Exception{
		when(policyRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(delete("/v1/api/policies/1")).andDo(print()).andExpect(status().is4xxClientError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'statusCode' : 404, 'message': 'Policy not found for id 1'}"));
	}
	
}
