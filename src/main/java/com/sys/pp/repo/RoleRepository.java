package com.sys.pp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.RolePK;
import com.sys.pp.model.Roles;

@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Roles, RolePK> {

}
