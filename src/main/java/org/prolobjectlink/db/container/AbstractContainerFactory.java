/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
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
