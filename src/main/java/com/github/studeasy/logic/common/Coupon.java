package com.github.studeasy.logic.common;

import java.util.*;

/**
 * 
 */
public class Coupon {

	private String title;
	private String description;
	private double value;
	private int quantity;
	private User owner; //Partner

	public Coupon(String title, String description, double value, int quantity, User owner) {
		this.title = title;
		this.description = description;
		this.value = value;
		this.quantity = quantity;
		this.owner = owner;
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}