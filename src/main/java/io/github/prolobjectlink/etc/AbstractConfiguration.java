/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2020 Prolobjectlink Project
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
package io.github.prolobjectlink.etc;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractConfiguration<K, V> extends AbstractMap<K, V> implements Map<K, V> {

	protected final Properties properties = new Properties();

	public final Map<K, V> asMap() {
		return this;
	}

	protected final File getConfiguration(String xmlFile) throws IOException {
		File configuration = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		if (prt.getCanonicalPath().contains("prolobjectlink-jpp")) { // dev mode
			configuration = new File(xmlFile);
		} else { // production mode
			configuration = new File(prt.getCanonicalPath() + File.separator + xmlFile);
		}
		return configuration;
	}

	protected final String getCurrentPath() {
		Class<?> c = getClass();
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + properties.hashCode();
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractConfiguration<K, V> other = (AbstractConfiguration<K, V>) obj;
		if (!properties.equals(other.properties)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UserManagement [properties=" + properties + "]";
	}

}
