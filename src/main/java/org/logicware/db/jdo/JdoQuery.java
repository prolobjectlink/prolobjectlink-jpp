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
package org.logicware.db.jdo;

import java.util.Collection;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class JdoQuery implements Query {

	@Override
	public void setClass(Class cls) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCandidates(Extent pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCandidates(Collection pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFilter(String filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void declareImports(String imports) {
		// TODO Auto-generated method stub

	}

	@Override
	public void declareParameters(String parameters) {
		// TODO Auto-generated method stub

	}

	@Override
	public void declareVariables(String variables) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOrdering(String ordering) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIgnoreCache(boolean ignoreCache) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getIgnoreCache() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void compile() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object execute(Object p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object execute(Object p1, Object p2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object execute(Object p1, Object p2, Object p3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object executeWithMap(Map parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object executeWithArray(Object... parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close(Object queryResult) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGrouping(String group) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUnique(boolean unique) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setResult(String data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setResultClass(Class cls) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRange(long fromIncl, long toExcl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRange(String fromInclToExcl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addExtension(String key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setExtensions(Map extensions) {
		// TODO Auto-generated method stub

	}

	@Override
	public FetchPlan getFetchPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long deletePersistentAll(Object... parameters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long deletePersistentAll(Map parameters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long deletePersistentAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setUnmodifiable() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnmodifiable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addSubquery(Query sub, String variableDeclaration, String candidateCollectionExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSubquery(Query sub, String variableDeclaration, String candidateCollectionExpression,
			String parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSubquery(Query sub, String variableDeclaration, String candidateCollectionExpression,
			String... parameters) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSubquery(Query sub, String variableDeclaration, String candidateCollectionExpression,
			Map parameters) {
		// TODO Auto-generated method stub

	}

}
