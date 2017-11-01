package de.devbrain.gigs.database.dao.utils;

import de.devbrain.gigs.database.model.Mapper;

import java.util.AbstractMap;

import static de.devbrain.gigs.database.model.Query.*;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class Parameter extends AbstractMap.SimpleEntry<String, Object> {
	
	private final ValueTypeFormat type;
	private final int             index;
	
	public Parameter(String name, int index, ValueTypeFormat type) {
		super(name, null);
		this.type = type;
		this.index = index;
	}
	
	public ValueTypeFormat getType() {
		return type;
	}
	
	public int getIndex() {
		return index;
	}
	
	public enum ValueTypeFormat {
		TEXT(p -> text(p.getValue()), "%s"),
		INT(p -> integer(p.getValue()), "%d"),
		TIME(p -> time((p.getValue())), "%s"),
		DATE(p -> date(p.getValue()), "%s"),
		DATE_TIME(p -> datetime(p.getValue()), "%s"),
		LIST(p -> joined(p.getValue()), "%s");
		
		private final Mapper<Parameter, Object> mapper;
		private final String                    holder;
		
		/**
		 * @param mapper Den Wert in ein String.format Parameter wandeln
		 * @param holder String.format Platzhalter
		 */
		ValueTypeFormat(Mapper<Parameter, Object> mapper, String holder) {
			this.mapper = mapper;
			this.holder = holder;
		}
		
		public Object map(Parameter p) {
			return mapper.map(p);
		}
		
		public String getStringFormatHolder() {
			return this.holder;
		}
	}
}
