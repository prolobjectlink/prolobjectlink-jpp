/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
