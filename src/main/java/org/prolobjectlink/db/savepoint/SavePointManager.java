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
package org.prolobjectlink.db.savepoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.prolobjectlink.AbstractWrapper;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Storage;

public final class SavePointManager extends AbstractWrapper implements Iterable<SavePointRecord> {

	private final Storage storage;
	private final List<SavePointRecord> records;
	private final PersistentContainer persistentContainer;

	public SavePointManager(PersistentContainer persistentContainer) {
		this(persistentContainer, new ArrayList<SavePointRecord>());
	}

	public SavePointManager(PersistentContainer persistentContainer, List<SavePointRecord> records) {
		this.storage = persistentContainer.getContainerFactory()
				.createStorage(persistentContainer.getLocation() + "/log/savepoint.pl");
		this.persistentContainer = persistentContainer;
		this.records = records;
	}

	public final PersistentContainer getPersistentContainer() {
		return persistentContainer;
	}

	public final Storage getStorage() {
		return storage;
	}

	public final List<SavePointRecord> getRecords() {
		return records;
	}

	public final void save(SavePointRecord from, SavePointRecord to) {
		save(from.getTime(), to.getTime());
	}

	public final void save(long from, long to) {
		for (SavePointRecord record : records) {
			long recordTime = record.getTime();
			if (recordTime >= from && recordTime <= to) {
				storage.begin();
				storage.insert(record);
				storage.commit();
			}
		}
	}

	public final void saveAll() {
		save(0, System.currentTimeMillis());
	}

	public final void commit() {
		// TODO Auto-generated method stub

	}

	public final void rollback() {
		// TODO Auto-generated method stub

	}

	public final boolean add(SavePointRecord record) {
		return records.add(record);
	}

	public final boolean remove(SavePointRecord record) {
		return records.remove(record);
	}

	public final boolean contains(SavePointRecord record) {
		return records.contains(record);
	}

	public final Iterator<SavePointRecord> iterator() {
		return records.iterator();
	}

	public final void clear() {
		records.clear();
	}

	public final boolean empty() {
		return records.isEmpty();
	}

	public final int size() {
		return records.size();
	}

}
