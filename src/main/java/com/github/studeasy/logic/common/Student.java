package com.github.studeasy.logic.common;

import java.util.*;

/**
 * 
 */
public class Student extends User {

	/**
	 * Default constructor
	 */
	public Student(String lastName,String firstName,String emailAddress,String password,int role,String pseudo) {
		super(lastName,firstName,emailAddress,password,role);
		this.pseudo=pseudo;
	}

	/**
	 * 
	 */
	private String pseudo;


	/**
	 * @return
	 */
	public String getPseudo() {
		// TODO implement here
		return "";
	}

	/**
	 * @param p
	 */
	public void setPseudo(String p) {
		// TODO implement here
	}

}