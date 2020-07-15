package com.adtrack.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table (name="UA_BLACKLIST")
public class UserAgentBlackList {

	@Id
	private String ua;
}
