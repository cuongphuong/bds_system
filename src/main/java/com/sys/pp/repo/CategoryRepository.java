package com.sys.pp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Category;

@Repository("CategoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query(value = "SELECT * FROM categorys WHERE category_id_parent <> 0", nativeQuery = true)
	List<Category> findByParentCategory();
	
}
