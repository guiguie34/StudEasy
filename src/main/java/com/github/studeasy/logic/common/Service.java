package com.github.studeasy.logic.common;

import java.util.*;

/**
 * 
 */
public class Service {

	private String title;
	private String description;
	private double cost;
	private String typeService;
	private User owner;
	private CategoryTag category;
	private String status;
	private List<CommandOfService> services;

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

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
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

	public Service(String title, String description, double cost, String typeService, User owner, CategoryTag category, String status) {
		this.title = title;
		this.description = description;
		this.cost = cost;
		this.typeService = typeService;
		this.owner = owner;
		this.category = category;
		this.status = status;
		this.services = new ArrayList<CommandOfService>();
	}

	public List<CommandOfService> getServices() {
		return services;
	}

	public void setServices(List<CommandOfService> services) {
		this.services = services;
	}
}