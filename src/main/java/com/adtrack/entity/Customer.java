package com.adtrack.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name="CUSTOMER")
public class Customer {
    
	@Id
    @GeneratedValue
	private int id;
	private String name ;
	private int active;
		
}
