package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

}
