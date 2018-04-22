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
package org.logicware.provider;

import java.util.StringTokenizer;

import javax.persistence.PersistenceException;

public final class JPAPersistenceVersion {

    private final String version;
    private final String xmlns;
    private final String xmlns_xsi;
    private final String xsi_schemaLocation;

    public JPAPersistenceVersion(String version, String xmlns, String xmlns_xsi, String xsi_schemaLocation) {
	this.version = version;
	this.xmlns = xmlns;
	this.xmlns_xsi = xmlns_xsi;
	this.xsi_schemaLocation = xsi_schemaLocation;
    }

    String getVersion() {
	return "" + this + "";
    }

    String getXmlns() {
	return xmlns;
    }

    String getXmlns_xsi() {
	return xmlns_xsi;
    }

    String getXsi_schemaLocation() {
	return xsi_schemaLocation;
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
	result = prime * result + ((xmlns_xsi == null) ? 0 : xmlns_xsi.hashCode());
	result = prime * result + ((xsi_schemaLocation == null) ? 0 : xsi_schemaLocation.hashCode());
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
	if (xmlns_xsi == null) {
	    if (other.xmlns_xsi != null)
		return false;
	} else if (!xmlns_xsi.equals(other.xmlns_xsi))
	    return false;
	if (xsi_schemaLocation == null) {
	    if (other.xsi_schemaLocation != null)
		return false;
	} else if (!xsi_schemaLocation.equals(other.xsi_schemaLocation))
	    return false;
	return true;
    }

    public String getFileName() {
	StringTokenizer stringTokenizer = new StringTokenizer(version, ".");
	if (stringTokenizer.countTokens() != 2) {
	    throw new PersistenceException("Schema version is not correct");
	}
	int major = Integer.valueOf(stringTokenizer.nextToken());
	int minor = Integer.valueOf(stringTokenizer.nextToken());
	return String.format("persistence_%d_%d.xsd", major, minor);
    }

}
