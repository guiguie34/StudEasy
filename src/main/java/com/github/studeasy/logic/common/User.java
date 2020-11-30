package com.github.studeasy.logic.common;

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
	private int role;


	/**
	 * Default constructor
	 */
	public User(String lastName,String firstName,String emailAddress,String password,int role) {
		this.lastname=lastName;
		this.firstname=firstName;
		this.emailAdress=emailAddress;
		this.password= password;
		this.role= role;

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

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}