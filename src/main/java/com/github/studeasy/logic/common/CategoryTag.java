package com.github.studeasy.logic.common;

/**
 * Class describing a category tag
 */
public class CategoryTag {

	/**
	 * Id of the category
	 */
	private int idCat;

	/**
	 * Name of the category
	 */
	private String name;

	/**
	 * Short description of the category
	 */
	private String description;

	public CategoryTag(int idCat, String name, String description) {
		this.idCat = idCat;
		this.name = name;
		this.description = description;
	}

	public int getIdCat() {
		return idCat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString(){
		return this.name + ": " + this.description;
	}
}