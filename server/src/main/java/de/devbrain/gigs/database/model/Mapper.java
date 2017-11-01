package de.devbrain.gigs.database.model;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public interface Mapper<E, TResult> {
	
	TResult map(E args);
}
