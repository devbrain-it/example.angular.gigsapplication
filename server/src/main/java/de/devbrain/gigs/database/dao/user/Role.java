package de.devbrain.gigs.database.dao.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class Role implements Serializable {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("roleName")
	private String roleName;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("isDefault")
	private boolean isDefault;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isDefault() {
		return isDefault;
	}
	
	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}
}
