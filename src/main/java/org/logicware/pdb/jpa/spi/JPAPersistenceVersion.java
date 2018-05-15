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
package org.logicware.pdb.jpa.spi;

import java.util.StringTokenizer;

import javax.persistence.PersistenceException;

public final class JPAPersistenceVersion {

	private final String version;
	private final String xmlns;
	private final String xmlnsXsi;
	private final String xsiSchemaLocation;

	public JPAPersistenceVersion(String version, String xmlns, String xmlnXsi, String xsiSchemaLocation) {
		this.version = version;
		this.xmlns = xmlns;
		this.xmlnsXsi = xmlnXsi;
		this.xsiSchemaLocation = xsiSchemaLocation;
	}

	String getVersion() {
		return "" + this + "";
	}

	String getXmlns() {
		return xmlns;
	}

	String getXmlnsXsi() {
		return xmlnsXsi;
	}

	String getXsiSchemaLocation() {
		return xsiSchemaLocation;
	}

	String getFileName() {
		StringTokenizer stringTokenizer = new StringTokenizer(version, ".");
		if (stringTokenizer.countTokens() != 2) {
			throw new PersistenceException("Schema version is not correct");
		}
		int major = Integer.parseInt(stringTokenizer.nextToken());
		int minor = Integer.parseInt(stringTokenizer.nextToken());
		return String.format("persistence_%d_%d.xsd", major, minor);
	}

	@Override
	public String toString() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result + ((xmlns == null) ? 0 : xmlns.hashCode());
		result = prime * result + ((xmlnsXsi == null) ? 0 : xmlnsXsi.hashCode());
		result = prime * result + ((xsiSchemaLocation == null) ? 0 : xsiSchemaLocation.hashCode());
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
		JPAPersistenceVersion other = (JPAPersistenceVersion) obj;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (xmlns == null) {
			if (other.xmlns != null)
				return false;
		} else if (!xmlns.equals(other.xmlns))
			return false;
		if (xmlnsXsi == null) {
			if (other.xmlnsXsi != null)
				return false;
		} else if (!xmlnsXsi.equals(other.xmlnsXsi))
			return false;
		if (xsiSchemaLocation == null) {
			if (other.xsiSchemaLocation != null)
				return false;
		} else if (!xsiSchemaLocation.equals(other.xsiSchemaLocation))
			return false;
		return true;
	}

}
