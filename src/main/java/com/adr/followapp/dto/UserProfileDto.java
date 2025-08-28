package com.adr.followapp.dto;

import java.time.LocalDate;

import com.adr.followapp.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserProfileDto {

//	private String userEmail;
	
	private String address;
	
	private byte[] profilePicture;
	
	@NotBlank(message = "firstName cannot be blank")
	private String firstName;

	@NotBlank(message = "lastName cannot be blank")
	private String lastName;
	
	private LocalDate dateOfBirth;
	
	private User user;

	public UserProfileDto() {
		super();		
	}
	
	public UserProfileDto(String address, byte[] profilePicture,
			String firstName,String lastName, LocalDate dateOfBirth, User user) {
		super();		
		this.address = address;
		this.profilePicture = profilePicture;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.user = user;
		
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
