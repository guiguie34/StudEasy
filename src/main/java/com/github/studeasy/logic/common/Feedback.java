package com.github.studeasy.logic.common;

import java.util.*;

/**
 * 
 */
public class Feedback {

	private String title;
	private int rate;
	private String comment;

	public Feedback(String title, int rate, String comment) {
		this.title = title;
		this.rate = rate;
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}