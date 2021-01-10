package com.github.studeasy.logic.common;

import java.util.*;

/**
 * Class describing a command of service
 */
public class CommandOfService {

	/**
	 * The id of the command
	 */
	private int idCommand;

	/**
	 * Feedback related to the command
	 */
	private Feedback feedback;

	/**
	 * User who commands the service
	 */
	private User owner;

	/**
	 * Service associated to the command
	 */
	private Service service;

	/**
	 * Status of the command (Pending,Validated,Declined)
	 */
	private int status;

	/**
	 * Date of the creation of the command
	 */
	private Date creationDate;

	public CommandOfService(int idCommand, Feedback feedback, User owner, Service service, int status, Date creationDate) {
		this.idCommand = idCommand;
		this.feedback = feedback;
		this.owner = owner;
		this.service = service;
		this.status = status;
		this.creationDate = creationDate;
	}

	public int getIdCommand() {
		return idCommand;
	}

	public void setIdCommand(int idCommand) {
		this.idCommand = idCommand;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}