package com.adtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adtrack.entity.UserAgentBlackList;

@Repository
public interface UserAgentRepository extends JpaRepository<UserAgentBlackList, String>{

}
