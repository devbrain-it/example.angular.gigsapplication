package de.devbrain.gigs.database.dao.gigs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class GigEntity {
	
	@JsonProperty("id")
	private final int id;
	
	@JsonProperty("artistGigs")
	private final List<ArtistGigEntity> artistGigs;
	
	@JsonProperty("time")
	private Date date;
	
	@JsonProperty("cityName")
	private String cityName;
	
	@JsonProperty("deleted")
	private boolean deleted;
	
	public GigEntity(int id) {
		this.id = id;
		this.artistGigs = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	
	public List<ArtistGigEntity> getArtistGigs() {
		return artistGigs;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
}
