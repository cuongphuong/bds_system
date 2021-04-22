package com.sys.pp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Province;

@Repository("ProvinceRepository")
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

}
