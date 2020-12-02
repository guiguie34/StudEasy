package com.github.studeasy.logic.common;

import java.util.*;

/**
 * 
 */
public class CommandOfService {

	private Feedback feedback;
	private User owner;
	private Service service;
	private String status;
	private Date creationDate;


	public CommandOfService(Feedback feedback, User owner, Service service, String status, Date creationDate) {
		this.feedback = feedback;
		this.owner = owner;
		this.service = service;
		this.status = status;
		this.creationDate = creationDate;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}



}