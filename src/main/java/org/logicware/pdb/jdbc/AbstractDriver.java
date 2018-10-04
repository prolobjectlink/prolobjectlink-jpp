/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.pdb.jdbc;

import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;

import org.logicware.pdb.prolog.PrologDriver;
import org.logicware.platform.logging.PlatformLogger;
import org.logicware.prolog.AbstractProvider;
import org.logicware.prolog.PrologAtom;
import org.logicware.prolog.PrologConverter;
import org.logicware.prolog.PrologDouble;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologFloat;
import org.logicware.prolog.PrologInteger;
import org.logicware.prolog.PrologList;
import org.logicware.prolog.PrologLong;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologStructure;
import org.logicware.prolog.PrologTerm;
import org.logicware.prolog.PrologVariable;

public abstract class AbstractDriver extends AbstractProvider implements PrologDriver {

	private static final int MAJOR = 1;
	private static final int MINOR = 1;

	private final PrologProvider provider;

	public AbstractDriver(PrologConverter<?> converter) {
		super(converter);
		this.provider = converter.createProvider();
	}

	public final DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		DriverPropertyInfo[] dis = new DriverPropertyInfo[info.size()];
		Iterator<Entry<Object, Object>> iter = info.entrySet().iterator();
		for (int i = 0; iter.hasNext(); i++) {
			Entry<Object, Object> entry = iter.next();
			String name = "" + entry.getKey() + "";
			String value = "" + entry.getValue() + "";
			dis[i] = new DriverPropertyInfo(name, value);
		}
		return dis;
	}

	public final boolean acceptsURL(String url) throws SQLException {
		return url.startsWith("jdbc:prolobjectlink:rempdb:") || url.startsWith("jdbc:prolobjectlink:mempdb:")
				|| url.startsWith("jdbc:prolobjectlink:file:");
	}

	public final int getMajorVersion() {
		return MAJOR;
	}

	public final int getMinorVersion() {
		return MINOR;
	}

	public final boolean jdbcCompliant() {
		return false;
	}

	public final Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return PlatformLogger.LOGGER;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractDriver other = (AbstractDriver) obj;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider)) {
			return false;
		}
		return true;
	}

	public final boolean isCompliant() {
		return provider.isCompliant();
	}

	public final PrologTerm prologNil() {
		return provider.prologNil();
	}

	public final PrologTerm prologCut() {
		return provider.prologCut();
	}

	public final PrologTerm prologFail() {
		return provider.prologFail();
	}

	public final PrologTerm prologTrue() {
		return provider.prologTrue();
	}

	public final PrologTerm prologFalse() {
		return provider.prologFalse();
	}

	public final PrologTerm prologEmpty() {
		return provider.prologEmpty();
	}

	public final PrologTerm parsePrologTerm(String term) {
		return provider.parsePrologTerm(term);
	}

	public final PrologTerm[] parsePrologTerms(String stringTerms) {
		return provider.parsePrologTerms(stringTerms);
	}

	public final PrologEngine newEngine() {
		return provider.newEngine();
	}

	public final PrologAtom newAtom(String functor) {
		return provider.newAtom(functor);
	}

	public final PrologFloat newFloat(Number value) {
		return provider.newFloat(value);
	}

	public final PrologDouble newDouble(Number value) {
		return provider.newDouble(value);
	}

	public final PrologInteger newInteger(Number value) {
		return provider.newInteger(value);
	}

	public final PrologLong newLong(Number value) {
		return provider.newLong(value);
	}

	public final PrologVariable newVariable() {
		return provider.newVariable();
	}

	public final PrologVariable newVariable(String name) {
		return provider.newVariable(name);
	}

	public final PrologVariable newVariable(int position) {
		return provider.newVariable(position);
	}

	public final PrologVariable newVariable(String name, int position) {
		return provider.newVariable(name, position);
	}

	public final PrologList newList() {
		return provider.newList();
	}

	public final PrologList newList(PrologTerm[] arguments) {
		return provider.newList(arguments);
	}

	public final PrologList newList(PrologTerm head, PrologTerm tail) {
		return provider.newList(head, tail);
	}

	public final PrologList newList(PrologTerm[] arguments, PrologTerm tail) {
		return provider.newList(arguments, tail);
	}

	public final PrologStructure newStructure(String functor, PrologTerm... arguments) {
		return provider.newStructure(functor, arguments);
	}

	public final PrologTerm newStructure(PrologTerm left, String operator, PrologTerm right) {
		return provider.newStructure(left, operator, right);
	}

	@Override
	public final String toString() {
		return "" + provider + "";
	}

}
