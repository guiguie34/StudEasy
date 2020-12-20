package com.github.studeasy.logic.common;

import com.github.studeasy.logic.common.role.Role;
import com.github.studeasy.logic.common.role.RoleAdmin;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.common.role.RoleStudent;

import java.util.*;

/**
 * Class describing a user
 */
public class User {

	/**
	 * Lastname of the user
	 */
	private String lastname;

	/**
	 * First name of the user
	 */
	private String firstname;

	/**
	 * Email of the user
	 */
	private String emailAdress;

	/**
	 * Password of the user
	 */
	private String password;

	/**
	 * Role of the user (Admin/Student/partner)
	 */
	private Role role;
	/**
	 * Notifications of the user
	 */
	private List<Notification> notifications;

	/**
	 * salt to encrypt/decrypt the password
	 */
	private String salt;
	/**
	 * pseudo
	 */
	private String pseudo;

	/**
	 * points of the student
	 */
	private int points;

	/**
	 * Default constructor
	 */
	public User(String lastName,String firstName,String emailAddress,String password,int role, String company,String pseudo, int points, String salt) {
		this.lastname=lastName;
		this.firstname=firstName;
		this.emailAdress=emailAddress;
		this.password= password;
		this.salt = salt;
		this.pseudo = pseudo;
		this.points = points;
		switch (role) {
			case 0:
				this.role = new RoleAdmin();
				break;
			case 1:
				this.role = new RoleStudent(pseudo,points);
				break;
			case 2:
				this.role = new RolePartner(company);
				break;
		}
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
	

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getSalt() {
		return salt;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}