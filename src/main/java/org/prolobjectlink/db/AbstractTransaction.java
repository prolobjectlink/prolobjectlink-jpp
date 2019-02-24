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
package org.prolobjectlink.db;

import java.util.Date;

import org.prolobjectlink.AbstractWrapper;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractTransaction extends AbstractWrapper implements Transaction {

	protected boolean active;
	private final long timestamp;

	public AbstractTransaction(long timestamp) {
		this.timestamp = timestamp;
	}

	protected void checkNonActiveTransaction() {
		if (!isActive()) {
			throw new IllegalStateException("Entity Transaction is not active");
		}
	}

	protected void checkActiveTransaction() {
		if (isActive()) {
			throw new IllegalStateException("Entity Transaction is active");
		}
	}

	public final boolean before(Transaction t) {
		return compareTo(t) < 0;
	}

	public final boolean after(Transaction t) {
		return compareTo(t) > 0;
	}

	public final int compareTo(Transaction o) {
		Date thisDate = new Date(timestamp);
		Date otherDate = new Date(o.getTimestamp());
		return thisDate.compareTo(otherDate);
	}

	public final long getTimestamp() {
		return timestamp;
	}

	public final boolean isActive() {
		return active;
	}

	public final void close() {
		active = false;
	}

}
