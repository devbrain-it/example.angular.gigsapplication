package de.devbrain.gigs.database.dao.gigs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class UserGigInfo implements UserGigJSON {
	
	@JsonProperty("gigId")
	private int gigId;
	
	@JsonProperty("artistIds")
	private List<Integer> artistIds;
	
	@Override
	public int getGigId() {
		return gigId;
	}
	
	public void setGigId(int gigId) {
		this.gigId = gigId;
	}
	
	@Override
	public List<Integer> getArtistIds() {
		return artistIds;
	}
	
	public void setArtistIds(List<Integer> artistIds) {
		this.artistIds = artistIds;
	}
}
