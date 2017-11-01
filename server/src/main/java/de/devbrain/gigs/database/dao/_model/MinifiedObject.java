package de.devbrain.gigs.database.dao._model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
@SuppressWarnings("unused")
public class MinifiedObject implements Serializable {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
