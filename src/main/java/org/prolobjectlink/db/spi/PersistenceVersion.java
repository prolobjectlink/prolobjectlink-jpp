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

	public String getFileName() {
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
