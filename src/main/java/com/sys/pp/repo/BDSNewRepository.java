package com.sys.pp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.BdsNew;

@Repository("BDSNewRepository")
public interface BDSNewRepository extends JpaRepository<BdsNew, Integer> {

	@Query(value = "SELECT count(*) FROM `bds_ news` n inner join detail_news d on n.news_id = d.news_id WHERE d.district_id = :districtId", nativeQuery = true)
	int coutnNewsByDistrictId(int districtId);

	@Query(value = "SELECT count(*) FROM `bds_ news` n inner join detail_news d on n.news_id = d.news_id WHERE d.district_id = :districtId and DATE_FORMAT(n.create_at, '%Y-%m-01') = DATE_FORMAT(SYSDATE(), '%Y-%m-01')", nativeQuery = true)
	int coutnNewsByDistrictIdOnMonth(int districtId);
}