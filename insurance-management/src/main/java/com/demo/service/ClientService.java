package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.ClientDTO;
import com.demo.entity.Client;
import com.demo.exception.ClientNotFoundException;
import com.demo.repository.ClientRepository;
import com.demo.util.Message;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	private static final String EXCEPTION_MESSAGE = "Client not found for id ";

	public List<Client> fetchAllClient() {
		return clientRepository.findAll();
	}

	public ClientDTO fetchClientById(Integer id) {
		Optional<Client> client = clientRepository.findById(id);
		if (client.isPresent()) {
			Client clientInfo = client.get();
			return new ClientDTO(id, clientInfo.getName(), clientInfo.getDateOfBirth(), clientInfo.getAddress(),
					clientInfo.getEmail(), clientInfo.getMobile(), clientInfo.getPolicies());
		}else {
			throw new ClientNotFoundException(EXCEPTION_MESSAGE+id);
		}
	}
	
	public Message createClient(Client client) {
		clientRepository.save(client);
		return new Message("New client saved");
	}
	
	public Message updateClient(Integer id, Client client) {
		
		Optional<Client> dbClient = clientRepository.findById(id);
		if (dbClient.isPresent()) {
			client.setId(id);
			clientRepository.save(client);
			return new Message("Client information updated");
		}else {
			throw new ClientNotFoundException(EXCEPTION_MESSAGE+id);
		}
		
	}
	
	public Message deleteClient(Integer id) {
		Optional<Client> client = clientRepository.findById(id);
		if(client.isPresent()) {
			String name = client.get().getName();
			clientRepository.deleteById(id);
			return new Message("Client "+name+" with id "+id+" removed");
		}else {
			throw new ClientNotFoundException(EXCEPTION_MESSAGE+id);
		}
		
	}

}
