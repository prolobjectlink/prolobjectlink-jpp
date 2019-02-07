/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.container;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.HierarchicalDatabase;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.RelationalDatabase;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.prolog.PrologObjectConverter;
import org.prolobjectlink.prolog.PrologConverter;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractContainerFactory implements ContainerFactory {

	private Settings properties;
	private PrologProvider provider;

	protected AbstractContainerFactory(Settings properties, PrologProvider provider) {
		this.properties = properties;
		this.provider = provider;
	}

	public final RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, String username,
			String password) {
		return createRelationalDatabase(storageMode, name, new DatabaseUser(username, password));
	}

	public final HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name, String username,
			String password) {
		return createHierarchicalDatabase(storageMode, name, new DatabaseUser(username, password));
	}

	public final ObjectConverter<PrologTerm> getObjectConverter() {
		return new PrologObjectConverter(provider);
	}

	public final void setProvider(PrologProvider provider) {
		this.provider = provider;
	}

	public final void setSettings(Settings properties) {
		this.properties = properties;
	}

	public final Settings getSettings() {
		return properties;
	}

	public final PrologProvider getProvider() {
		return provider;
	}

	public final <K> PrologConverter<K> getConverter() {
		return provider.getConverter();
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
