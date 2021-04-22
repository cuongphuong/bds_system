package com.sys.pp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.District;

@Repository("DistrictRepository")
public interface DistrictRepository extends JpaRepository<District, Integer> {

	@Query(value = "SELECT * FROM district WHERE _province_id = :id", nativeQuery = true)
	List<District> findByProvinceId(Integer id);
}
