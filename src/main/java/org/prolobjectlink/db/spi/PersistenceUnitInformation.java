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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.prolobjectlink.db.DatabaseUnitInfo;

public final class PersistenceUnitInformation extends AbstractUnitInfo implements DatabaseUnitInfo {

	private static final String ALL = "ALL";
	private static final String NONE = "NONE";
	private static final String AUTO = "AUTO";
	private static final String CALLBACK = "CALLBACK";
	private static final String UNSPECIFIED = "UNSPECIFIED";
	private static final String ENABLE_SELECTIVE = "ENABLE_SELECTIVE";
	private static final String DISABLE_SELECTIVE = "DISABLE_SELECTIVE";

	private final PersistenceUnitTransactionType transactionType;
	private ValidationMode validationMode = ValidationMode.AUTO;
	private SharedCacheMode sharedCacheMode = SharedCacheMode.UNSPECIFIED;
	private final Set<ClassTransformer> classTransformers = new HashSet<ClassTransformer>();

	public PersistenceUnitInformation(URL unitRootUrl, PersistenceSchemaVersion xmlVersion,
			PersistenceVersion persistenceVersion, String unitName, PersistenceUnitTransactionType transactionType) {
		super(unitRootUrl, xmlVersion, persistenceVersion, unitName);
		this.transactionType = transactionType;
	}

	public PersistenceUnitTransactionType getTransactionType() {
		return transactionType;
	}

	public SharedCacheMode getSharedCacheMode() {
		return sharedCacheMode;
	}

	public ValidationMode getValidationMode() {
		return validationMode;
	}

	public void addTransformer(ClassTransformer transformer) {
		classTransformers.add(transformer);
	}

	public void setValidationMode(String mode) {
		if (mode.equals(NONE)) {
			validationMode = ValidationMode.NONE;
		} else if (mode.equals(AUTO)) {
			validationMode = ValidationMode.AUTO;
		} else if (mode.equals(CALLBACK)) {
			validationMode = ValidationMode.CALLBACK;
		}
	}

	public void setSharedCacheMode(String mode) {
		if (mode.equals(ALL)) {
			sharedCacheMode = SharedCacheMode.ALL;
		} else if (mode.equals(NONE)) {
			sharedCacheMode = SharedCacheMode.NONE;
		} else if (mode.equals(UNSPECIFIED)) {
			sharedCacheMode = SharedCacheMode.UNSPECIFIED;
		} else if (mode.equals(ENABLE_SELECTIVE)) {
			sharedCacheMode = SharedCacheMode.ENABLE_SELECTIVE;
		} else if (mode.equals(DISABLE_SELECTIVE)) {
			sharedCacheMode = SharedCacheMode.DISABLE_SELECTIVE;
		}
	}

	public final Set<ClassTransformer> getClassTransformers() {
		return classTransformers;
	}

	@Override
	public String toString() {
		return "PersistenceUnitInformation [getPersistenceUnitName()=" + getPersistenceUnitName() + "]";
	}

}
