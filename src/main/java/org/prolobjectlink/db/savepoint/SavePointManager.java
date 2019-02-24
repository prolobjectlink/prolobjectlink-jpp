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
