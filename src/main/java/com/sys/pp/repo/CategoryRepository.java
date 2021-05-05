package com.sys.pp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Category;

@Repository("CategoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query(value = "SELECT * FROM categorys WHERE category_id_parent = 0", nativeQuery = true)
	List<Category> findByParentCategory();

	@Query(value = "SELECT count(*) FROM `bds_ news` n inner join detail_news d on n.news_id = d.news_id WHERE n.category_id = :categoryId and formality in (:formality)", nativeQuery = true)
	int countByCategory(int categoryId, List<String> formality);
	
	@Query(value = "SELECT count(*) FROM `bds_ news` n inner join detail_news d on n.news_id = d.news_id WHERE formality in (:formality)", nativeQuery = true)
	int countAllByFormatly(List<String> formality);
	
	@Query(value = "SELECT count(*) FROM `bds_ news` n inner join detail_news d on n.news_id = d.news_id WHERE formality in (:formality) and DATE_FORMAT(n.create_at, '%Y-%m-01') = DATE_FORMAT(SYSDATE(), '%Y-%m-01')", nativeQuery = true)
	int countAllByFormatlyInday(List<String> formality);
}
