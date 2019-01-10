/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 WorkLogic Project
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
package org.worklogic.db.jdo;

import javax.jdo.PersistenceManager;
import javax.transaction.Synchronization;

import org.worklogic.db.Transaction;

public class JdoTransaction implements javax.jdo.Transaction {

	private final Transaction tx;

	public JdoTransaction(Transaction tx) {
		this.tx = tx;
	}

	public void begin() {
		tx.begin();
	}

	public void commit() {
		tx.commit();
	}

	public void rollback() {
		tx.rollback();
	}

	public boolean isActive() {
		return tx.isActive();
	}

	public void setRollbackOnly() {
		// TODO Auto-generated method stub

	}

	public boolean getRollbackOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNontransactionalRead(boolean nontransactionalRead) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getNontransactionalRead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNontransactionalWrite(boolean nontransactionalWrite) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getNontransactionalWrite() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRetainValues(boolean retainValues) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getRetainValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRestoreValues(boolean restoreValues) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getRestoreValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOptimistic(boolean optimistic) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getOptimistic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getIsolationLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIsolationLevel(String level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSynchronization(Synchronization sync) {
		// TODO Auto-generated method stub

	}

	@Override
	public Synchronization getSynchronization() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
