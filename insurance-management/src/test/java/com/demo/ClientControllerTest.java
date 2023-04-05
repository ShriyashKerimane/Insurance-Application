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

import com.demo.dto.ClientDTO;
import com.demo.entity.Client;
import com.demo.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	ClientRepository clientRepository;

	@Test
	void testFetchAllClients() throws Exception {
		List<Client> list = new ArrayList<>();
		list.add(new Client(1, "abc", "2020-01-01", "xyz", "abc@nowhere.com", "1234567890", null));
		when(clientRepository.findAll()).thenReturn(list);
		mvc.perform(get("/v1/api/clients/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						"[{'id' : 1, 'name' : abc, 'dateOfBirth' : '2020-01-01', 'address' : xyz, 'email' : 'abc@nowhere.com', 'mobile' : '1234567890'}]"));

	}

	@Test
	void testFetchClientByIdPass() throws Exception {
		Client client = new Client(1, "abc", "2020-01-01", "xyz", "abc@nowhere.com", "1234567890", null);
		when(clientRepository.findById(1)).thenReturn(Optional.of(client));
		mvc.perform(get("/v1/api/clients/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						"{'id' : 1, 'name' : abc, 'dateOfBirth' : '2020-01-01', 'address' : xyz, 'email' : 'abc@nowhere.com', 'mobile' : '1234567890'}"));

	}

	@Test
	void testFetchClientByIdFail() throws Exception {
		when(clientRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(get("/v1/api/clients/1")).andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{'statusCode' : 404, 'message': 'Client not found for id 1'}"));

	}

	@Test
	void testCreateClient() throws Exception {
		ClientDTO clientDTO = new ClientDTO(1, "abc", "2020-01-01", "xyz", "abc@nowhere.com", "1234567890", null);
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(clientDTO);

		mvc.perform(post("/v1/api/clients/").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("New client saved"));
	}

	@Test
	void testUpdateClientPass() throws Exception {
		Client client = new Client(1, "abc", "2020-01-01", "xyz", "abc@nowhere.com", "1234567890", null);
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(client);
		
		when(clientRepository.findById(1)).thenReturn(Optional.of(client));
		mvc.perform(put("/v1/api/clients/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Client information updated"));
	}
	
	@Test
	void testUpdateClientFail() throws Exception {
		Client client = new Client(1, "abc", "2020-01-01", "xyz", "abc@nowhere.com", "1234567890", null);
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(client);
		
		when(clientRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(put("/v1/api/clients/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{'statusCode' : 404, 'message': 'Client not found for id 1'}"));
	}

	@Test
	void testDeleteClientPass() throws Exception {
		Client client = new Client(1, "abc", "2020-01-01", "xyz", "abc@nowhere.com", "1234567890", null);
		when(clientRepository.findById(1)).thenReturn(Optional.of(client));
		mvc.perform(delete("/v1/api/clients/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Client abc with id 1 removed"));
	}
	
	@Test
	void testDeleteClientFail() throws Exception {
		when(clientRepository.findById(1)).thenReturn(Optional.empty());
		mvc.perform(delete("/v1/api/clients/1")).andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{'statusCode' : 404, 'message': 'Client not found for id 1'}"));
	}

}
