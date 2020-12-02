package com.github.studeasy.logic.common;

/**
 * Class describing a category tag
 */
public class CategoryTag {

	/**
	 * Name of the category
	 */
	private String name;

	/**
	 * Short description of the category
	 */
	private String description;

	public CategoryTag(String name, String description) {
		this.name = name;
		this.description = description;
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
}