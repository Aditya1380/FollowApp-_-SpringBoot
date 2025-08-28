package com.adr.followapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adr.followapp.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	boolean existsByRoleName(String roleName);
	Role findByRoleName(String roleName);
}
