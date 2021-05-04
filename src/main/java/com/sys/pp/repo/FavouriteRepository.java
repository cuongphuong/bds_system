package com.sys.pp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sys.pp.model.Favourite;
import com.sys.pp.model.FavouritePK;

public interface FavouriteRepository extends JpaRepository<Favourite, FavouritePK> {
	
}
