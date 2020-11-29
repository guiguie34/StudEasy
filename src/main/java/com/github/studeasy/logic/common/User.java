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
	/**
	 * @return
	 */
	public String getLastname() {
		// TODO implement here
		return "";
	}

	/**
	 * @param l
	 */
	public void setLastname(String l) {
		// TODO implement here
	}

	/**
	 * @return
	 */
	public String getFirstname() {
		// TODO implement here
		return "";
	}

	/**
	 * @param f
	 */
	public void setFirstname(String f) {
		// TODO implement here
	}

	/**
	 * @return
	 */
	public String getEmailAdress() {
		// TODO implement here
		return this.emailAdress;
	}

	/**
	 * @param e
	 */
	public void setEmailAdress(String e) {
		// TODO implement here
	}

	/**
	 * @return
	 */
	public String getPassword() {
		// TODO implement here
		return "";
	}

	/**
	 * @param p
	 */
	public void setPassword(String p) {
		// TODO implement here
	}

	/**
	 * @return
	 */
	public int getRole() {
		// TODO implement here
		return 0;
	}

	/**
	 * @param r
	 */
	public void setRole(int r) {
		// TODO implement here
	}

}