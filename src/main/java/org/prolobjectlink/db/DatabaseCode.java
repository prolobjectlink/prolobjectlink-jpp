/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologProvider;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class DatabaseCode<E extends SchemaElement<?>> extends AbstractElement<E> implements Serializable {

	private final String path;
	private final List<String> parameters;
	private final transient List<String> instructions;

	private transient PrologEngine engine;
	private transient PrologProvider provider;

	private static final long serialVersionUID = 7552979263681672426L;

	protected DatabaseCode() {
		// for internal reflection
		this.instructions = new ArrayList<String>();
		this.parameters = new ArrayList<String>();
		this.provider = null;
		this.engine = null;
		this.path = null;
	}

	public DatabaseCode(String name, String comment, Schema schema, String path, PrologProvider provider) {
		super(name, comment, schema);
		this.instructions = new ArrayList<String>();
		this.parameters = new ArrayList<String>();
		this.engine = provider.newEngine();
		this.provider = provider;
		this.schema = schema;
		this.path = path;
	}

	public final String getDescriptor() {
		// TODO bad descriptor string. No have comma between parameters
		StringBuilder builder = new StringBuilder();
		builder.append(getName());
		builder.append('(');
		for (String s : getParameters()) {
			builder.append(s);
		}
		builder.append(')');
		return "" + builder + "";
	}

	public final String getCode() {
		String n = getName();
		StringBuilder buffer = new StringBuilder();
		Iterator<String> i = parameters.iterator();
		String regex = "\\.|\\?|#|[a-z][A-Za-z0-9_]*";
		buffer.append(n.matches(regex) ? n : "'" + n + "'");
		buffer.append('(');
		while (i.hasNext()) {
			buffer.append(i.next());
			if (i.hasNext()) {
				buffer.append(',');
			}
		}
		buffer.append(')');
		buffer.append(' ');
		buffer.append(":-");
		buffer.append(' ');
		buffer.append('\n');
		buffer.append('\t');
		i = instructions.iterator();
		while (i.hasNext()) {
			buffer.append(i.next());
			if (i.hasNext()) {
				buffer.append(',');
			}
		}
		return "" + buffer + "";
	}

	public abstract DatabaseCodeType getType();

	public E addInstructions(String code) {
		instructions.add(code);
		return (E) this;
	}

	public final List<String> getInstructions() {
		return instructions;
	}

	public final List<String> getParameters() {
		return parameters;
	}

	public final boolean containsParameter(String string) {
		return parameters.contains(string);
	}

	public final String getPath() {
		return path;
	}

	public final PrologEngine getEngine() {
		return engine;
	}

	public final void setEngine(PrologEngine engine) {
		this.engine = engine;
	}

	public final PrologProvider getProvider() {
		return provider;
	}

	public final void setProvider(PrologProvider provider) {
		this.provider = provider;
	}

	public abstract E save();

	@Override
	public final String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseCode<?> other = (DatabaseCode<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
