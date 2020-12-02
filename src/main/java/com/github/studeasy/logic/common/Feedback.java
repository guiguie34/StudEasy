package com.github.studeasy.logic.common;

/**
 * Class describing a feedback
 */
public class Feedback {

	/**
	 * Title of the feedback
	 */
	private String title;

	/**
	 * Rate provided by the owner of the command (between 0 and 5)
	 */
	private int rate;

	/**
	 * Short comment about the service
	 */
	private String comment;

	/**
	 * Service related to the feedback
	 */
	private CommandOfService service;

	public Feedback(String title, int rate, String comment, CommandOfService service) {
		this.title = title;
		this.rate = rate;
		this.comment = comment;
		this.service=service;
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

	public CommandOfService getService() {
		return service;
	}

	public void setService(CommandOfService service) {
		this.service = service;
	}
}