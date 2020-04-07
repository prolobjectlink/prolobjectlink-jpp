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
package io.github.prolobjectlink.db.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import io.github.prolobjectlink.db.Predicate;
import io.github.prolobjectlink.db.Storage;
import io.github.prolobjectlink.db.StoragePool;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;

public final class StoragePoolExecutor {

	private final StoragePool pool;
	private final ExecutorService executor;

	public StoragePoolExecutor(ExecutorService executor, StoragePool pool) {
		this.executor = executor;
		this.pool = pool;
	}

	public final List<Object> findAll(String string) {
		List<Object> list = Collections.synchronizedList(new ArrayList<Object>());
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			Callable<List<Object>> worker = new StorageFindAllString(storage, string);
			Future<List<Object>> result = getExecutor().submit(worker);
			try {
				for (Object object : result.get()) {
					if (!list.contains(object)) {
						list.add(object);
					}
				}
			} catch (InterruptedException e) {
				LoggerUtils.error(getClass(), LoggerConstants.INTERRUPTED_ERROR, e);
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				LoggerUtils.error(getClass(), LoggerConstants.EXECUTION_ERROR, e);
			}
		}
		return list;
	}

	public final List<Object> findAll(String functor, Object... args) {
		List<Object> list = Collections.synchronizedList(new ArrayList<Object>());
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			Callable<List<Object>> worker = new StorageFindAllStringArray(storage, functor, args);
			Future<List<Object>> result = getExecutor().submit(worker);
			try {
				for (Object object : result.get()) {
					if (!list.contains(object)) {
						list.add(object);
					}
				}
			} catch (InterruptedException e) {
				LoggerUtils.error(getClass(), LoggerConstants.INTERRUPTED_ERROR, e);
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				LoggerUtils.error(getClass(), LoggerConstants.EXECUTION_ERROR, e);
			}
		}
		return list;
	}

	public final <O> List<O> findAll(O o) {
		List<O> list = Collections.synchronizedList(new ArrayList<O>());
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			Callable<List<O>> worker = new StorageFindAllObject<O>(storage, o);
			Future<List<O>> result = getExecutor().submit(worker);
			try {
				for (O object : result.get()) {
					if (!list.contains(object)) {
						list.add(object);
					}
				}
			} catch (InterruptedException e) {
				LoggerUtils.error(getClass(), LoggerConstants.INTERRUPTED_ERROR, e);
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				LoggerUtils.error(getClass(), LoggerConstants.EXECUTION_ERROR, e);
			}
		}
		return list;
	}

	public final <O> List<O> findAll(Class<O> clazz) {
		List<O> list = Collections.synchronizedList(new ArrayList<O>());
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			Callable<List<O>> worker = new StorageFindAllClass<O>(storage, clazz);
			Future<List<O>> result = getExecutor().submit(worker);
			try {
				for (O object : result.get()) {
					if (!list.contains(object)) {
						list.add(object);
					}
				}
			} catch (InterruptedException e) {
				LoggerUtils.error(getClass(), LoggerConstants.INTERRUPTED_ERROR, e);
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				LoggerUtils.error(getClass(), LoggerConstants.EXECUTION_ERROR, e);
			}
		}
		return list;
	}

	public final <O> List<O> findAll(Predicate<O> predicate) {
		List<O> list = Collections.synchronizedList(new ArrayList<O>());
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			Callable<List<O>> worker = new StorageFindAllPredicate<O>(storage, predicate);
			Future<List<O>> result = getExecutor().submit(worker);
			try {
				for (O object : result.get()) {
					if (!list.contains(object)) {
						list.add(object);
					}
				}
			} catch (InterruptedException e) {
				LoggerUtils.error(getClass(), LoggerConstants.INTERRUPTED_ERROR, e);
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				LoggerUtils.error(getClass(), LoggerConstants.EXECUTION_ERROR, e);
			}
		}
		return list;
	}

	public final void begin() {
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			getExecutor().execute(new StorageBegin(storage));
		}
	}

	public final void commit() {
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			getExecutor().execute(new StorageCommit(storage));
		}
	}

	public final void open() {
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			getExecutor().execute(new StorageOpen(storage));
		}
	}

	public final void flush() {
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			getExecutor().execute(new StorageFlush(storage));
		}
	}

	public final void clear() {
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			getExecutor().execute(new StorageClear(storage));
		}
	}

	public final void close() {
		for (int i = 0; i < getPool().getStorages().size(); i++) {
			Storage storage = getPool().getStorages().get(i);
			getExecutor().execute(new StorageClose(storage));
		}
	}

	public final ExecutorService getExecutor() {
		return executor;
	}

	public final void shutdown() {
		executor.shutdown();
	}

	public final StoragePool getPool() {
		return pool;
	}

	private abstract class AbstractRunnable implements Runnable {

		private final Storage storage;

		private AbstractRunnable(Storage storage) {
			this.storage = storage;
		}

		public Storage getStorage() {
			return storage;
		}

	}

	private abstract class AbstractCallable<V> implements Callable<V> {

		private final Storage storage;

		private AbstractCallable(Storage storage) {
			this.storage = storage;
		}

		public Storage getStorage() {
			return storage;
		}

	}

	private final class StorageFindAllString extends AbstractCallable<List<Object>> implements Callable<List<Object>> {

		private final String query;

		private StorageFindAllString(Storage storage, String q) {
			super(storage);
			this.query = q;
		}

		public List<Object> call() throws Exception {
			return getStorage().findAll(query);
		}

	}

	private final class StorageFindAllStringArray extends AbstractCallable<List<Object>>
			implements Callable<List<Object>> {

		private final String functor;
		private final Object[] arguments;

		private StorageFindAllStringArray(Storage storage, String functor, Object[] arguments) {
			super(storage);
			this.functor = functor;
			this.arguments = arguments;
		}

		public List<Object> call() throws Exception {
			return getStorage().findAll(functor, arguments);
		}

	}

	private final class StorageFindAllObject<O> extends AbstractCallable<List<O>> implements Callable<List<O>> {

		private final O o;

		private StorageFindAllObject(Storage storage, O o) {
			super(storage);
			this.o = o;
		}

		public List<O> call() throws Exception {
			return getStorage().findAll(o);
		}

	}

	private final class StorageFindAllClass<O> extends AbstractCallable<List<O>> implements Callable<List<O>> {

		private final Class<O> clazz;

		private StorageFindAllClass(Storage storage, Class<O> clazz) {
			super(storage);
			this.clazz = clazz;
		}

		public List<O> call() throws Exception {
			return getStorage().findAll(clazz);
		}

	}

	private final class StorageFindAllPredicate<O> extends AbstractCallable<List<O>> implements Callable<List<O>> {

		private final Predicate<O> predicate;

		private StorageFindAllPredicate(Storage storage, Predicate<O> p) {
			super(storage);
			this.predicate = p;
		}

		public List<O> call() throws Exception {
			return getStorage().findAll(predicate);
		}

	}

	private final class StorageCommit extends AbstractRunnable implements Runnable {

		private StorageCommit(Storage storage) {
			super(storage);
		}

		public void run() {
			getStorage().commit();
		}

	}

	private final class StorageBegin extends AbstractRunnable implements Runnable {

		private StorageBegin(Storage storage) {
			super(storage);
		}

		public void run() {
			getStorage().begin();
		}

	}

	private final class StorageOpen extends AbstractRunnable implements Runnable {

		private StorageOpen(Storage storage) {
			super(storage);
		}

		public void run() {
			getStorage().open();
		}

	}

	private final class StorageFlush extends AbstractRunnable implements Runnable {

		private StorageFlush(Storage storage) {
			super(storage);
		}

		public void run() {
			if (getStorage().isDirty()) {
				getStorage().flush();
			}
		}

	}

	private final class StorageClear extends AbstractRunnable implements Runnable {

		private StorageClear(Storage storage) {
			super(storage);
		}

		public void run() {
			getStorage().clear();
		}

	}

	private final class StorageClose extends AbstractRunnable implements Runnable {

		private StorageClose(Storage storage) {
			super(storage);
		}

		public void run() {
			getStorage().clear();
			getStorage().close();
		}

	}

}
