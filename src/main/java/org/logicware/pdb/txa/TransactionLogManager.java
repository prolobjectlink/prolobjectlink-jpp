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
package org.logicware.pdb.txa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.PersistentContainer;

public final class TransactionLogManager implements Iterable<TransactionLogRecord> {

	private final PersistentContainer container;
	private final ContainerFactory containerFactory;
	private final List<TransactionLogRecord> records;

	public TransactionLogManager(ContainerFactory containerFactory) {
		this(containerFactory, new ArrayList<TransactionLogRecord>());
	}

	public TransactionLogManager(ContainerFactory containerFactory, List<TransactionLogRecord> records) {
		this.container = containerFactory.createStorage("log/transaction-log.pl");
		this.containerFactory = containerFactory;
		this.records = records;
	}

	public final PersistentContainer getContainer() {
		return container;
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public final List<TransactionLogRecord> getRecords() {
		return records;
	}

	public final void save(TransactionLogRecord from, TransactionLogRecord to) {
		save(from.getTime(), to.getTime());
	}

	public final void save(long from, long to) {
		for (TransactionLogRecord record : records) {
			long recordTime = record.getTime();
			if (recordTime > from && recordTime < to) {
				container.insert(record);
			}
		}
	}

	public final void saveAll() {
		save(0, System.currentTimeMillis());
	}

	public final boolean add(TransactionLogRecord record) {
		return records.add(record);
	}

	public final boolean remove(TransactionLogRecord record) {
		return records.remove(record);
	}

	public final boolean contains(TransactionLogRecord record) {
		return records.contains(record);
	}

	public Iterator<TransactionLogRecord> iterator() {
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

	public final void sort() {
		Collections.sort(records);
	}

}
