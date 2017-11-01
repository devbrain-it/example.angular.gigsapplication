package de.devbrain.gigs.utils;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class Validate {
	
	public static void notNull(Object o) {
		if(o == null) {
			throw new IllegalArgumentException("Validated Object was null!");
		}
	}
}
