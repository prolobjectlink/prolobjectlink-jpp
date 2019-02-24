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
package org.prolobjectlink.db.spi;

import java.util.StringTokenizer;

public final class PersistenceVersion {

	private final String version;
	private final String xmlns;
	private final String xmlnsXsi;
	private final String xsiSchemaLocation;

	public PersistenceVersion(String version, String xmlns, String xmlnXsi, String xsiSchemaLocation) {
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
			throw new RuntimeException("Schema version is not correct");
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
		PersistenceVersion other = (PersistenceVersion) obj;
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
