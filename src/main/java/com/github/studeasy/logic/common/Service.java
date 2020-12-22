package com.github.studeasy.logic.common;

import java.util.*;

/**
 * Class describing a service
 */
public class Service {

	/**
	 * The id of the service
	 */
	private int idService;

	/**
	 * Title of the service
	 */
	private String title;

	/**
	 * Description of the service
	 */
	private String description;

	/**
	 * Cost of the service
	 */
	private int cost;

	/**
	 * Describe the type of the service
	 * 0 proposed
	 * 1 requested
	 */
	private int typeService;

	/**
	 * Reference to the user who has created the service
	 */
	private User owner;

	/**
	 * Describe the category of the service (example: Tutoring...)
	 */
	private CategoryTag category;

	/**
	 * Describe the status of the service
	 * 0 pending
	 * 1 validated
	 */
	private int status;

	/**
	 * The date and time when it was created
	 */
	private Date dateCreation;

	/**
	 * Reference to the orders containing this service
	 */
	private List<CommandOfService> services;

	public Service(int idService, String title, String description, int cost, int typeService, User owner, CategoryTag category, int status, Date dateCreation) {
		this.idService = idService;
		this.title = title;
		this.description = description;
		this.cost = cost;
		this.typeService = typeService;
		this.owner = owner;
		this.category = category;
		this.status = status;
		this.dateCreation = dateCreation;
		this.services = new ArrayList<>();
	}

	public int getIdService() {
		return idService;
	}

	public void setIdService(int idService) {
		this.idService = idService;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getTypeService() {
		return typeService;
	}

	public void setTypeService(int typeService) {
		this.typeService = typeService;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public CategoryTag getCategory() {
		return category;
	}

	public void setCategory(CategoryTag category) {
		this.category = category;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public List<CommandOfService> getServices() {
		return services;
	}

	public void setServices(List<CommandOfService> services) {
		this.services = services;
	}
}