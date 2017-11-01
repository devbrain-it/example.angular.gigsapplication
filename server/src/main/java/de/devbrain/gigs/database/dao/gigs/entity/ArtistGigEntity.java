package de.devbrain.gigs.database.dao.gigs.entity;

import java.util.Date;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class ArtistGigEntity {
	
	private String artistName;
	private Date   time;
	private String genreName;
	private int id;
	
	public String getArtistName() {
		return artistName;
	}
	
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getGenreName() {
		return genreName;
	}
	
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
