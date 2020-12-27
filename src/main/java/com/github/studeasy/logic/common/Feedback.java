package com.github.studeasy.logic.common;

import java.sql.Date;

/**
 * Class describing a feedback
 */
public class Feedback {

	/**
	 * id of the feedback
	 */
	private int idFeedback;

	/**
	 * Title of the feedback
	 */
	private String title;

	/**
	 * Rate provided by the owner of the command (between 0 and 5)
	 */
	private int rate;

	private Date date;

	/**
	 * Short comment about the service
	 */
	private String comment;


	public Feedback(int idFeedback,String title,String comment ,Date date, int rate) {
		this.title = title;
		this.rate = rate;
		this.comment = comment;
		this.date = date;
		this.idFeedback = idFeedback;
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

	public int getIdFeedback() {
		return idFeedback;
	}

	public void setIdFeedback(int idFeedback) {
		this.idFeedback = idFeedback;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}