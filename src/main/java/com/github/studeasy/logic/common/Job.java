package com.github.studeasy.logic.common;

import java.util.*;

/**
 *
 */
public class Job {


	 private String title;
	 private String localisation;
	 private String role;
	 private String start;
	 private String duration;
	 private String description;
	 private String contactMail;
	 private String contactPhone;
	 private User owner;


	public Job(String title, String localisation, String role, String start, String duration, String description, String contactMail, String contactPhone, User owner) {
		this.title = title;
		this.localisation = localisation;
		this.role = role;
		this.start = start;
		this.duration = duration;
		this.description = description;
		this.contactMail = contactMail;
		this.contactPhone = contactPhone;
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}