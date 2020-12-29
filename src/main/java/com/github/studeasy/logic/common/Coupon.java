package com.github.studeasy.logic.common;

/**
 * Class describing a coupon
 */
public class Coupon {

	/**
	 * The id of the coupon
	 */
	private int id;

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
	private int value;

	/**
	 * Quantity available the current coupon
	 */
	private int quantity;

	/**
	 * Partner to which belongs the coupon
	 */
	private User owner;

	public Coupon(int id,String title, String description, int value, int quantity, User owner) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.value = value;
		this.quantity = quantity;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
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