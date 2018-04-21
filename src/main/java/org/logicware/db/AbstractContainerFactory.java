/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.db;

import org.logicware.ContainerFactory;
import org.logicware.DatabaseUser;
import org.logicware.HierarchicalDatabase;
import org.logicware.Settings;
import org.logicware.RelationalDatabase;
import org.logicware.prolog.PrologProvider;

public abstract class AbstractContainerFactory implements ContainerFactory {

	private Settings properties;
	private PrologProvider provider;

	protected AbstractContainerFactory(Settings properties, PrologProvider provider) {
		this.properties = properties;
		this.provider = provider;
	}

	public RelationalDatabase createRelationalDatabase(String name, String username, String password) {
		return createRelationalDatabase(name, new DatabaseUser(username, password));
	}

	public HierarchicalDatabase createHierarchicalDatabase(String name, String username, String password) {
		return createHierarchicalDatabase(name, new DatabaseUser(username, password));
	}

	public final void setProvider(PrologProvider provider) {
		this.provider = provider;
	}

	public final void setProperties(Settings properties) {
		this.properties = properties;
	}

	public final Settings getProperties() {
		return properties;
	}

	public final PrologProvider getProvider() {
		return provider;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
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
		AbstractContainerFactory other = (AbstractContainerFactory) obj;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "[properties=" + properties + ", provider=" + provider + "]";
	}

}
