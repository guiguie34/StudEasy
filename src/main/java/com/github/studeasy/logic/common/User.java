package com.github.studeasy.logic.common;

import com.github.studeasy.logic.common.role.Role;
import com.github.studeasy.logic.common.role.RoleAdmin;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.common.role.RoleStudent;

import java.util.*;

/**
 * 
 */
public class User {



	/**
	 * 
	 */
	private String lastname;

	/**
	 * 
	 */
	private String firstname;

	/**
	 * 
	 */
	private String emailAdress;

	/**
	 * 
	 */
	private String password;

	/**
	 * 
	 */
	private Role role;

	private List<Notification> notifications;



	/**
	 * Default constructor
	 */
	public User(String lastName,String firstName,String emailAddress,String password,int role, String company,String pseudo, int points) {
		this.lastname=lastName;
		this.firstname=firstName;
		this.emailAdress=emailAddress;
		this.password= password;
		switch (role) {
			case 0 -> this.role = new RoleAdmin();
			case 1 -> this.role = new RoleStudent(pseudo,points);
			case 2 -> this.role = new RolePartner(company);
		}
	}
	public User(){

	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}