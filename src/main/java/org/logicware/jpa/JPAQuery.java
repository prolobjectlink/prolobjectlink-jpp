/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpa;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.Tuple;

import org.logicware.pdb.DatabaseService;
import org.logicware.pdb.ObjectReflector;

/**
 * 
 * @author Jose Zalacain
 * 
 */
public class JPAQuery implements Query {

	/** Fix the first position of result */
	protected int firstResult = 0;

	/** Fix maximum of result number */
	protected int maxResult = Integer.MAX_VALUE;

	protected static final String WILD_CARD = "X_";

	/** Query language string */
	protected final String qlString;
	// protected Class<?> resultClass;
	private final Class<?>[] resultClasses;
	protected final List<Tuple> tuples = new ArrayList<Tuple>();

	protected final Map<String, Object> hints;

	protected int parameterPosition;
	protected List<Parameter<?>> parameters;

	protected FlushModeType flushMode = FlushModeType.AUTO;
	protected LockModeType lockMode = LockModeType.NONE;

	protected final DatabaseService database;

	protected final ObjectReflector reflector = new ObjectReflector();

	public JPAQuery(DatabaseService database, String qlString) {
		this(database, qlString, Object[].class);
	}

	public JPAQuery(DatabaseService database, String qlString, Class<?> resultClass) {
		this(database, qlString, new Class<?>[] { resultClass });
	}

	public JPAQuery(DatabaseService database, String qlString, Class<?>[] resultClasses) {
		this.parameters = new ArrayList<Parameter<?>>();
		this.hints = new HashMap<String, Object>();
		this.resultClasses = resultClasses;
		this.database = database;
		this.qlString = qlString;
	}

	public List getResultList() {
		int size = maxResult < tuples.size() ? maxResult : tuples.size();
		List<Object> solutionList = new ArrayList<Object>(size);
		for (int i = firstResult; i < size; i++) {
			Tuple tuple = tuples.get(i);
			if (resultClasses[0] != Object[].class) {
				// String alias = resultClasses[0].getSimpleName();
				String alias = "";
				Class<?> type = resultClasses[0];
				solutionList.add(tuple.get(new JPATupleElement(alias, type)));
			} else {
				solutionList.add(tuple.toArray());
			}
		}
		return solutionList;
	}

	public Object getSingleResult() {

		if (firstResult < 0 || firstResult >= tuples.size()) {
			throw new NoResultException("The query no have result");
		}

		Object result = null;

		if (resultClasses[0] != Object[].class) {
			// String alias = resultClasses[0].getSimpleName();
			String alias = "";
			Class<?> type = resultClasses[0];
			result = tuples.get(firstResult).get(new JPATupleElement<Object>(alias, type));
		} else {
			result = tuples.get(firstResult).toArray();
		}

		if (result == null) {
			throw new NoResultException("The query no have result");
		}

		return result;

	}

	public int executeUpdate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Query setMaxResults(int maxResult) {
		this.maxResult = maxResult;
		return this;
	}

	public int getMaxResults() {
		return maxResult;
	}

	public Query setFirstResult(int startPosition) {
		this.firstResult = startPosition;
		return this;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public Query setHint(String hintName, Object value) {
		hints.put(hintName, value);
		return this;
	}

	public Map<String, Object> getHints() {
		return hints;
	}

	public <T> Query setParameter(Parameter<T> param, T value) {
		if (param instanceof JPAParameter) {
			JPAParameter<T> parameter = (JPAParameter<T>) param;
			parameter.setValue(value);
			parameters.add(param);
		}
		return this;
	}

	public Query setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(Parameter<Calendar>, Calendar, TemporalType");
	}

	public Query setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(Parameter<Date>, Date, TemporalType");
	}

	public Query setParameter(String name, Object value) {
		parameters.add(new JPAParameter(name, parameterPosition++, value.getClass(), value));
		return this;
	}

	public Query setParameter(String name, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(String, Calendar, TemporalType");
	}

	public Query setParameter(String name, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("String, Date, TemporalType");
	}

	public Query setParameter(int position, Object value) {
		parameters.add(new JPAParameter(String.valueOf(position), position, value.getClass()));
		return this;
	}

	public Query setParameter(int position, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(int, Calendar, TemporalType");
	}

	public Query setParameter(int position, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("int, Date, TemporalType");
	}

	public Set<Parameter<?>> getParameters() {
		return new AbstractSet<Parameter<?>>() {

			@Override
			public Iterator<Parameter<?>> iterator() {
				return parameters.iterator();
			}

			@Override
			public int size() {
				return parameters.size();
			}
		};
	}

	public Parameter<?> getParameter(String name) {
		for (Parameter<?> parameter : parameters) {
			if (parameter.getName().equals(name)) {
				return parameter;
			}
		}
		return null;
	}

	public <T> Parameter<T> getParameter(String name, Class<T> type) {
		Parameter<?> parameter = getParameter(name);
		if (parameter != null && parameter.getParameterType() == type) {
			return (Parameter<T>) parameter;
		}
		return null;
	}

	public Parameter<?> getParameter(int position) {
		Set<Parameter<?>> set = getParameters();
		for (Parameter<?> parameter : set) {
			if (parameter.getPosition() == position) {
				return parameter;
			}
		}
		return null;
	}

	public <T> Parameter<T> getParameter(int position, Class<T> type) {
		Set<Parameter<?>> set = getParameters();
		for (Parameter<?> parameter : set) {
			if (parameter.getPosition() == position && parameter.getParameterType() == type) {
				return (Parameter<T>) parameter;
			}
		}
		return null;
	}

	public boolean isBound(Parameter<?> param) {
		Parameter<?> parameter = parameters.get(param.getPosition());
		if (parameter instanceof JPAParameter) {
			JPAParameter<?> logicParameter = (JPAParameter<?>) parameter;
			return logicParameter.getValue() != null;
		}
		return false;
	}

	public <T> T getParameterValue(Parameter<T> param) {
		return (T) getParameterValue(param.getName());
	}

	public Object getParameterValue(String name) {
		Parameter<?> parameter = getParameter(name);
		if (parameter instanceof JPAParameter) {
			JPAParameter<?> logicParameter = (JPAParameter<?>) parameter;
			return logicParameter.getValue();
		}
		return null;
	}

	public Object getParameterValue(int position) {
		Parameter<?> parameter = parameters.get(position);
		if (parameter instanceof JPAParameter) {
			JPAParameter<?> logicParameter = (JPAParameter<?>) parameter;
			return logicParameter.getValue();
		}
		return null;
	}

	public Query setFlushMode(FlushModeType flushMode) {
		this.flushMode = flushMode;
		return this;
	}

	public FlushModeType getFlushMode() {
		return flushMode;
	}

	public Query setLockMode(LockModeType lockMode) {
		this.lockMode = lockMode;
		return this;
	}

	public LockModeType getLockMode() {
		return lockMode;
	}

	public <T> T unwrap(Class<T> cls) {
		if (cls.equals(JPAQuery.class)) {
			return (T) this;
		} else if (cls.equals(JPATypedQuery.class)) {
			if (this instanceof JPATypedQuery) {
				return (T) this;
			}
		} else if ((cls.equals(JPAStoredProcedureQuery.class)) && (this instanceof JPAStoredProcedureQuery)) {
			return (T) this;
		}
		throw new PersistenceException("Impossible unwrap to " + cls.getName());
	}

}
