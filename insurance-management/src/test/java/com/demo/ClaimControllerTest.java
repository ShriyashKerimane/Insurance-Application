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

import com.demo.dto.ClaimDTO;
import com.demo.entity.Claim;
import com.demo.entity.Policy;
import com.demo.repository.ClaimRepository;
import com.demo.repository.PolicyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ClaimControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	PolicyRepository policyRepository;
	
	@MockBean
	ClaimRepository claimRepository;

	@Test
	void testFetchAllClaims() throws Exception {
		List<Claim> list = new ArrayList<>();
		list.add(new Claim(1, "first", "2020-01-01", "claimed"));
		when(claimRepository.findAll()).thenReturn(list);
		mvc.perform(get("/v1/api/claims/")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json("[{'claimNumber' : 1, 'description' : 'first', 'claimDate' : '2020-01-01', 'claimStatus' : 'claimed'}]"));
	}
	
	@Test
	void testFetchClaimByIdPass() throws Exception{
		Claim claim = new Claim(1, "first", "2020-01-01", "claimed");
		when(claimRepository.findById(1)).thenReturn(Optional.of(claim));
		mvc.perform(get("/v1/api/claims/1")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'claimNumber' : 1, 'description' : 'first', 'claimDate' : '2020-01-01', 'claimStatus' : 'claimed'}"));
		
	}
	
	@Test
	void testFetchClaimByIdFail() throws Exception{
		when(claimRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(get("/v1/api/claims/1")).andDo(print()).andExpect(status().is4xxClientError())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().json(
				"{'statusCode' : 404, 'message': 'Claim not found for id 1'}"));
	}
	
	@Test
	void testCreateClaimPass() throws Exception{
		ClaimDTO claimDTO = new ClaimDTO(1, "first", "2020-01-01", "claimed", 1);
		Policy policy = new Policy("life", 100000.0, 1000.0, "2023-01-01", "2025-01-01");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(claimDTO);
		
		when(policyRepository.findById(1)).thenReturn(Optional.of(policy));
		mvc.perform(post("/v1/api/claims/").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
			.andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("New claim saved for policy id : 1"));
	}
	
	@Test
	void testCreateClaimFail() throws Exception{
		ClaimDTO claimDTO = new ClaimDTO(1, "first", "2020-01-01", "claimed", 1);		
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(claimDTO);
		
		when(policyRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(post("/v1/api/claims/").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
			.andDo(print()).andExpect(status().is4xxClientError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'statusCode' : 404, 'message': 'Policy not found for id 1'}"));
	}
	
	@Test
	void testUpdateClaimPass() throws Exception{
		Claim claim = new Claim(1, "first", "2020-01-01", "claimed");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(claim);
		
		when(claimRepository.findById(1)).thenReturn(Optional.of(claim));
		mvc.perform(put("/v1/api/claims/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
			.andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Claim Updated"));
	}
	
	@Test
	void testUpdateClaimFail() throws Exception{
		Claim claim = new Claim(1, "first", "2020-01-01", "claimed");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(claim);
		
		when(claimRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(put("/v1/api/claims/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
			.andDo(print()).andExpect(status().is4xxClientError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'statusCode' : 404, 'message': 'Claim not found for id 1'}"));
	}
	
	@Test
	void testDeleteClaimPass() throws Exception{
		Claim claim = new Claim(1, "first", "2020-01-01", "claimed");
		when(claimRepository.findById(1)).thenReturn(Optional.of(claim));
		mvc.perform(delete("/v1/api/claims/1")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Claim with id 1 removed"));
	}
	
	@Test
	void testDeleteClaimFail() throws Exception{
		when(claimRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(delete("/v1/api/claims/1")).andDo(print()).andExpect(status().is4xxClientError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"{'statusCode' : 404, 'message': 'Claim not found for id 1'}"));
	}
	
}
