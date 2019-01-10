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
package org.worklogic.db.jdo.spi;

import javax.jdo.PersistenceManager;
import javax.jdo.spi.PersistenceCapable;
import javax.jdo.spi.StateManager;

public class JdoPersistenceCapable implements PersistenceCapable {

	@Override
	public PersistenceManager jdoGetPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void jdoReplaceStateManager(StateManager sm) throws SecurityException {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoProvideField(int fieldNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoProvideFields(int[] fieldNumbers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoReplaceField(int fieldNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoReplaceFields(int[] fieldNumbers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoReplaceFlags() {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoCopyFields(Object other, int[] fieldNumbers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoMakeDirty(String fieldName) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object jdoGetObjectId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object jdoGetTransactionalObjectId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object jdoGetVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean jdoIsDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean jdoIsTransactional() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean jdoIsPersistent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean jdoIsNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean jdoIsDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean jdoIsDetached() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PersistenceCapable jdoNewInstance(StateManager sm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceCapable jdoNewInstance(StateManager sm, Object oid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object jdoNewObjectIdInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object jdoNewObjectIdInstance(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void jdoCopyKeyFieldsToObjectId(Object oid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoCopyKeyFieldsToObjectId(ObjectIdFieldSupplier fm, Object oid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jdoCopyKeyFieldsFromObjectId(ObjectIdFieldConsumer fm, Object oid) {
		// TODO Auto-generated method stub

	}

}
