package com.github.studeasy.logic.common;

import java.util.*;

/**
 * 
 */
public class Service {

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
	 * Describe the type of the service (Service proposed/asked)
	 */
	private String typeService;
	/**
	 * Reference to the user who has created the service
	 */
	private User owner;
	/**
	 * Describe the category of the service (example: Tutoring...)
	 */
	private CategoryTag category;
	/**
	 * Describe the status of the service (pending/validated)
	 */
	private String status;
	/**
	 * Reference to the orders containing this service
	 */
	private List<CommandOfService> services;

	public Service(String title, String description, int cost, String typeService, User owner, CategoryTag category, String status) {
		this.title = title;
		this.description = description;
		this.cost = cost;
		this.typeService = typeService;
		this.owner = owner;
		this.category = category;
		this.status = status;
		this.services = new ArrayList<>();
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

	public String getTypeService() {
		return typeService;
	}

	public void setTypeService(String typeService) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public List<CommandOfService> getServices() {
		return services;
	}

	public void setServices(List<CommandOfService> services) {
		this.services = services;
	}
}