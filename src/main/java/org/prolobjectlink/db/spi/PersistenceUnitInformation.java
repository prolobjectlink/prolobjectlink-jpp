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

import java.net.URL;

import org.prolobjectlink.db.DatabaseUnitInfo;

public final class PersistenceUnitInformation extends AbstractUnitInfo implements DatabaseUnitInfo {

	protected static final String ALL = "ALL";
	protected static final String NONE = "NONE";
	protected static final String AUTO = "AUTO";
	protected static final String CALLBACK = "CALLBACK";
	protected static final String UNSPECIFIED = "UNSPECIFIED";
	protected static final String ENABLE_SELECTIVE = "ENABLE_SELECTIVE";
	protected static final String DISABLE_SELECTIVE = "DISABLE_SELECTIVE";

	protected final String transactionType;
	protected String sharedCacheMode;
	protected String validationMode;

	public PersistenceUnitInformation(URL unitRootUrl, PersistenceSchemaVersion xmlVersion,
			PersistenceVersion persistenceVersion, String unitName, String transactionType) {
		super(unitRootUrl, xmlVersion, persistenceVersion, unitName);
		this.transactionType = transactionType;
	}

	public void setValidationMode(String mode) {
		this.validationMode = mode;
	}

	public void setSharedCacheMode(String mode) {
		this.sharedCacheMode = mode;
	}

	@Override
	public String toString() {
		return "PersistenceUnitInformation [getPersistenceUnitName()=" + getPersistenceUnitName() + "]";
	}

}
