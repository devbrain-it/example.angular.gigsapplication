package de.devbrain.gigs.database.dao.artist;

import de.devbrain.gigs.database.dao.IEditDao;
import de.devbrain.gigs.database.dao._model.MinifiedObject;

import java.util.Date;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public interface IArtistDao extends IEditDao<MinifiedObject> {
	
	void linkToGig(int artistId, int gigId, int genreId, Date time);
	
	void unlinkFromGig(int artist, int gigId);
}
