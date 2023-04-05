package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
