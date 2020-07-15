package com.adtrack.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adtrack.entity.IpBalcklist;

@Repository
public interface IpRepository extends JpaRepository<IpBalcklist, BigInteger>{

}
