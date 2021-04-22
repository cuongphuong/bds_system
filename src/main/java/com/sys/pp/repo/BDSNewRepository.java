package com.sys.pp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.BdsNew;

@Repository("BDSNewRepository")
public interface BDSNewRepository extends JpaRepository<BdsNew, Integer> {

}