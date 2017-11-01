package de.devbrain.gigs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class GigData {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("genre")
	private String genre;
	
	@JsonProperty("artist")
	private String artist;
	
	@JsonProperty("artistId")
	private int artistId;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("time")
	private String time;
	
	@JsonProperty("deleted")
	private boolean deleted;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public int getArtistId() {
		return artistId;
	}
	
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
}
