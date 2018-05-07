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

public abstract class AbstractElement implements SchemaElement, Serializable {

	protected final String name;
	protected String comment;
	protected transient Schema schema;
	private static final long serialVersionUID = -1360649262423474799L;

	protected AbstractElement() {
		// for internal reflection
		this.comment = null;
		this.schema = null;
		this.name = null;
	}

	public AbstractElement(String name, String comment, Schema schema) {
		this.name = name;
		this.comment = comment;
		this.schema = schema;
	}

	public final String getName() {
		return name;
	}

	public final Schema getSchema() {
		return schema;
	}

	public final String getComment() {
		return comment;
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
		AbstractElement other = (AbstractElement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}