package com.github.studeasy.logic.common;

/**
 * Class describing a coupon
 */
public class Coupon {

	/**
	 * Title of the coupon
	 */
	private String title;

	/**
	 * Short description for the coupon
	 */
	private String description;

	/**
	 * Value of the coupon in the partner store (in €)
	 */
	private double value;

	/**
	 * Cost of the coupon
	 */
	private int cost;

	/**
	 * Quantity available the current coupon
	 */
	private int quantity;

	/**
	 * Partner to which belongs the coupon
	 */
	private User owner;

	public Coupon(String title, String description, double value, int cost, int quantity, User owner) {
		this.title = title;
		this.description = description;
		this.value = value;
		this.quantity = quantity;
		this.owner = owner;
		this.cost= cost;
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}