package com.sys.pp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Street;

@Repository("StreetRepository")
public interface StreetRepository extends JpaRepository<Street, Integer> {

	@Query(value = "SELECT * FROM street WHERE _district_id = :id", nativeQuery = true)
	List<Street> findStreetByDistrictId(Integer id);
}
