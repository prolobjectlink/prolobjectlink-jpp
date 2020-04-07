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
package io.github.prolobjectlink.db;

import java.util.Date;

import io.github.prolobjectlink.AbstractWrapper;

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
