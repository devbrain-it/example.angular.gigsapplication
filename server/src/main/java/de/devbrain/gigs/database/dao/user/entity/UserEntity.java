package de.devbrain.gigs.database.dao.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
@SuppressWarnings("unused")
public class UserEntity {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("visibleName")
	private String visibleName;
	
	@JsonProperty("login")
	private String login;
	
	@JsonProperty("roleId")
	private int roleId;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setVisibleName(String visibleName) {
		this.visibleName = visibleName;
	}
	
	public String getVisibleName() {
		return visibleName;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
