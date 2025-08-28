package com.adr.followapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adr.followapp.dto.RoleDto;
import com.adr.followapp.service.RoleService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Tag(description = "Add Role APIs", name = "Role handling APIs")
@RequestMapping("/api/role")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		super();
		this.roleService = roleService;
	}
	
	@PostMapping("/add-roles")
	public ResponseEntity<String> addRole(@RequestBody RoleDto roleDto) {
		String message = roleService.addRole(roleDto);
		return ResponseEntity.ok(message);
	}
	
}
