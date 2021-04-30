package com.sys.pp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.NewsType;

@Repository("NewsTypeRepository")
public interface NewsTypeRepository extends JpaRepository<NewsType, Integer> {

}
