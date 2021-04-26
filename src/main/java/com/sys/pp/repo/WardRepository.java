package com.sys.pp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Ward;

@Repository("WardRepository")
public interface WardRepository extends JpaRepository<Ward, Integer> {

	@Query(value = "SELECT * FROM ward WHERE _district_id = :id", nativeQuery = true)
	List<Ward> findByDistrictId(Integer id);
}
