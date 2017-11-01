package de.devbrain.gigs.database.dao;

import de.devbrain.gigs.database.model.IDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singleton;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public interface IEditDao<T extends Serializable> extends IDao {
	
	int countAll();
	
	default List<T> fetchAll() {
		return getRange(0, countAll());
	}
	
	List<T> getRange(int index, int length);
	
	int insert(T singleInsert);
	
	void save(T single);
	
	default void delete(int id) {
		delete(singleton(id));
	}
	
	void delete(Collection<Integer> ids);
	
	T find(int id);
}
