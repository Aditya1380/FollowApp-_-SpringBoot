package com.adr.followapp.service.impl;

import org.springframework.stereotype.Service;

import com.adr.followapp.dto.RoleDto;
import com.adr.followapp.entity.Role;
import com.adr.followapp.repository.RoleRepository;
import com.adr.followapp.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	@Override
	public String addRole(RoleDto roleDto) {		
		Role role = new Role();
		role.setRoleName(roleDto.getRoleName());
		if(roleRepository.existsByRoleName(roleDto.getRoleName())) {
			return "Role alreay exist in DB!";
		}
		roleRepository.save(role);
		return "Role added Successfully!";
	}

}
