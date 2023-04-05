package com.demo.dto;

import java.util.List;

import com.demo.entity.Policy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
	
	private Integer id = null;
	private String name;
	private String dateOfBirth;
	private String address;
	private String email;
	private String mobile;
	private List<Policy> policies= null;

}
