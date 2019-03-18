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

//	public final <K> PrologConverter<K> getConverter() {
//		return provider.getConverter();
//	}

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
