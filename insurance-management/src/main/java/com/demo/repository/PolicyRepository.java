package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {

}
