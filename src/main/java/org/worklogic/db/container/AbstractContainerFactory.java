/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.worklogic.db.container;

import org.logicware.prolog.PrologConverter;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;
import org.worklogic.db.ContainerFactory;
import org.worklogic.db.DatabaseUser;
import org.worklogic.db.HierarchicalDatabase;
import org.worklogic.db.ObjectConverter;
import org.worklogic.db.RelationalDatabase;
import org.worklogic.db.StorageMode;
import org.worklogic.db.etc.Settings;
import org.worklogic.db.prolog.PrologObjectConverter;

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
