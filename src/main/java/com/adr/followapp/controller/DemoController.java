package com.adr.followapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Hello world api",description = "This is a simple API which returns the string.")
@RequestMapping("/api/logged")
public class DemoController {

	@GetMapping("/hello")
	public String helloworld() {
		return "Hello world";
	}

}
