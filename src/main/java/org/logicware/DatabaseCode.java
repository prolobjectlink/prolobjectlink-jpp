/*
 * #%L
 * prolobjectlink
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
package org.logicware;

import java.io.Serializable;
import java.util.List;

import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologProvider;
import org.logicware.util.ArrayList;

public abstract class DatabaseCode implements Serializable {

	public enum CodifiableType {

		VIEW("View", 0), FUNCTION("Function", 1);

		private final String name;
		private final int ordinal;

		CodifiableType(String name, int ordinal) {
			this.ordinal = ordinal;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public int getOrdinal() {
			return ordinal;
		}

	}

	private final String name;
	private final String path;
	protected transient String code;
	private transient CodifiableType type;
	private final List<String> parameters;
	protected transient DatabaseSchema schema;
	private final transient PrologEngine engine;
	private final transient PrologProvider provider;
	private static final long serialVersionUID = 7552979263681672426L;

	public DatabaseCode() {
		this.parameters = new ArrayList<String>();
		this.provider = null;
		this.schema = null;
		this.engine = null;
		this.path = null;
		this.name = null;
		this.type = null;
	}

	public DatabaseCode(CodifiableType type, String path, String name, DatabaseSchema schema, PrologProvider provider) {
		this.parameters = new ArrayList<String>();
		this.engine = provider.newEngine();
		this.provider = provider;
		this.schema = schema;
		this.path = path;
		this.name = name;
		this.type = type;
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
		return code;
	}

	public abstract DatabaseCode setCode(String code);

	public final DatabaseSchema getSchema() {
		return schema;
	}

	public abstract DatabaseCode setSchema(DatabaseSchema schema);

	public final List<String> getParameters() {
		return parameters;
	}

	public boolean containsParameter(String string) {
		return parameters.contains(string);
	}

	public final String getName() {
		return name;
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
		if (getCode() == null) {
			throw new PersistenceError("The " + type.name + " " + getName() + " dont't have code");
		}
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
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
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
