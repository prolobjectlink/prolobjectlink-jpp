/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.logicware.pdb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.logicware.pdb.prolog.PrologEngine;
import org.logicware.pdb.prolog.PrologProvider;

public abstract class DatabaseCode extends AbstractElement implements Serializable {

	private final String path;
	private final List<String> parameters;
	private final transient PrologEngine engine;
	private final transient PrologProvider provider;
	protected final transient List<String> instructions;
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

	public abstract DatabaseCode addInstructions(String code);

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

	public final PrologProvider getProvider() {
		return provider;
	}

	public final DatabaseCode save() {
		getEngine().consult(getPath());
		getEngine().assertz(getCode());
		getEngine().persist(getPath());
		return this;
	}

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
		DatabaseCode other = (DatabaseCode) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
