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
package io.github.prolobjectlink.db.spi;

import java.net.URL;

import io.github.prolobjectlink.db.DatabaseUnitInfo;

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
