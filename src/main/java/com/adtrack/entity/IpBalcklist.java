package com.adtrack.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table (name="IP_BLACKLIST")
public class IpBalcklist {
    
	@Id
	private BigInteger ip;
}
