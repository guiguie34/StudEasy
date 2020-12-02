package com.github.studeasy.logic.common;

import java.util.*;

/**
 * 
 */
public class Notification {

	private String title;
	private String description;
	private boolean read;
	private User user;

	public Notification(String title, String description, boolean read, User user) {
		this.title = title;
		this.description = description;
		this.read = read;
		this.user = user;
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
}