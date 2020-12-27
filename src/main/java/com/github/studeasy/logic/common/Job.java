package com.github.studeasy.logic.common;

/**
 * Class describing the job class
 */
public class Job {

	/**
	 * Id of the job
 	 */
	private int idJob;

	/**
	 * Title of the job
	 */
	private String title;

	/**
	 * localisation of the job.
	 */
	private String localisation;

	/**
	 * describe the role of the person who will want to do the job
	 */
	private String role;

	/**
	 * Describe when the job will start (example: next week, next month, or more precisely 12/08/2021)
	 */
	private String start;

	/**
	 * Describe the duration of the job
	 */
	 private String duration;

	/**
	 * Description of the job
	 */
	private String description;

	/**
	 * Mail of the creator of the job
	 */
	 private String contactMail;

	/**
	 * Phone number of the creator of the job
	 */
	private String contactPhone;

	/**
	 * References to the user who created the job
	 */
	 private User owner;

	public Job(int id, String title, String localisation, String role, String start, String duration, String description, String contactMail, String contactPhone, User owner) {
		this.idJob=id;
		this.title = title;
		this.localisation = localisation;
		this.role = role;
		this.start = start;
		this.duration = duration;
		this.description = description;
		this.contactMail = contactMail;
		this.contactPhone = contactPhone;
		this.owner = owner;
	}

	public int getIdJob() {return idJob;}

	public void setIdJob(int id){
		this.idJob=id;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}