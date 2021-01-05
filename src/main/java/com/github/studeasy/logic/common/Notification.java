package com.github.studeasy.logic.common;

/**
 * Class describing a notification
 */
public class Notification {


	/**
	 * Id of notification
	 */
	private int id;

	/**
	 * Title of the notification
	 */
	private String title;

	/**
	 * Description of the notification
	 */
	private String description;

	/**
	 * Boolean that indicates if the notification as been read
	 */
	private boolean read;

	/**
	 * Reference to the user who has created the notification
	 */
	private User user;

	public Notification(int id, String title, String description, boolean read, User user) {
		this.title = title;
		this.description = description;
		this.read = read;
		this.user = user;
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

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}