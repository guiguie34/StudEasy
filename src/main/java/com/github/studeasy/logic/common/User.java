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
	 * Id of the user
	 */
	private int idUser;

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
	private String emailAddress;

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
	 * Default constructor
	 */
	public User(int idUser, String lastName,String firstName,String emailAddress,String password,int role, String company,String pseudo, int points, String salt) {
		this.idUser = idUser;
		this.lastname=lastName;
		this.firstname=firstName;
		this.emailAddress=emailAddress;
		this.password= password;
		this.salt = salt;
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

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}