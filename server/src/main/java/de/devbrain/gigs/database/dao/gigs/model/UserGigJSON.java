package de.devbrain.gigs.database.dao.gigs.model;

import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public interface UserGigJSON {
	
	int getGigId();
	
	List<Integer> getArtistIds();
}
