package com.sys.pp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Project;

@Repository("ProjectRepository")
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	@Query(value = "SELECT * FROM project WHERE _district_id = :id", nativeQuery = true)
	List<Project> findProjectByDistrictId(Integer id);
}
