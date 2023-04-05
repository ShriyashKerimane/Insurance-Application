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

import com.demo.dto.ClientDTO;
import com.demo.entity.Client;
import com.demo.service.ClientService;
import com.demo.util.Message;

@RestController
@RequestMapping(path = "/v1/api/clients")
public class ClientController {
	
	@Autowired
	ClientService clientService;

	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Client>> fetchAllClients() {
		return ResponseEntity.ok(clientService.fetchAllClient());
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDTO> fetchClientById(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(clientService.fetchClientById(id));
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> createClient(@RequestBody ClientDTO clientDTO) {
		Client client = new Client(clientDTO.getName(), clientDTO.getDateOfBirth(), clientDTO.getAddress(),
				clientDTO.getEmail(), clientDTO.getMobile());
		return ResponseEntity.ok(clientService.createClient(client));
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> createClient(@PathVariable("id") Integer id ,@RequestBody ClientDTO clientDTO) {
		Client client = new Client(clientDTO.getName(), clientDTO.getDateOfBirth(), clientDTO.getAddress(),
				clientDTO.getEmail(), clientDTO.getMobile());
		return ResponseEntity.ok(clientService.updateClient(id,client));
	}
	
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteClient(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(clientService.deleteClient(id));
	}
	

}
