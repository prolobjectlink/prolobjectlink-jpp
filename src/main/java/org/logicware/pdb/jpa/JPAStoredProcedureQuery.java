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
package org.logicware.pdb.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.FlushModeType;
import javax.persistence.Parameter;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;

import org.logicware.pdb.DatabaseEngine;
import org.logicware.pdb.ProcedureQuery;

public final class JPAStoredProcedureQuery extends JPAQuery implements StoredProcedureQuery {

	private boolean hasMoreResult;
	private final String procedureName;
	private final String[] resultSetMappings;

	public JPAStoredProcedureQuery(DatabaseEngine database, String procedureName) {
		this(database, procedureName, new Class<?>[] { Object[].class }, null);
	}

	public JPAStoredProcedureQuery(DatabaseEngine database, String procedureName, Class<?>[] resultClasses) {
		this(database, procedureName, resultClasses, null);
	}

	public JPAStoredProcedureQuery(DatabaseEngine database, String procedureName, String[] resultSetMappings) {
		this(database, procedureName, new Class<?>[] { Object[].class }, resultSetMappings);
	}

	public JPAStoredProcedureQuery(DatabaseEngine database, String procedureName, Class<?>[] resultClasses,
			String[] resultSetMappings) {
		super(database, procedureName, resultClasses);
		this.procedureName = procedureName;
		this.resultSetMappings = resultSetMappings;
	}

	public StoredProcedureQuery registerStoredProcedureParameter(int position, Class type, ParameterMode mode) {
		Parameter<?> parameter = new JPAParameter(WILD_CARD + String.valueOf(position), position, type, mode);
		setParameter(parameter, null);
		return this;
	}

	public StoredProcedureQuery registerStoredProcedureParameter(String parameterName, Class type, ParameterMode mode) {
		Parameter<?> parameter = new JPAParameter(parameterName, parameterPosition++, type, mode);
		setParameter(parameter, null);
		return this;
	}

	public Object getOutputParameterValue(int position) {
		Parameter<?> parameter = getParameter(position);
		return getOutputParameterValue(parameter.getName());
	}

	public Object getOutputParameterValue(String parameterName) {
		Parameter<?> parameter = getParameter(parameterName);
		if (parameter instanceof JPAParameter) {
			JPAParameter<?> logicParameter = (JPAParameter<?>) parameter;
			ParameterMode mode = logicParameter.getParameterMode();
			if (mode == ParameterMode.INOUT || mode == ParameterMode.OUT) {
				return getParameterValue(parameterName);
			}
		}
		throw new PersistenceException("The parameter '" + parameterName + "' is not register in some out mode");
	}

	public boolean execute() {

		//
		int parametersNumber = parameters.size();
		String[] arguments = new String[parametersNumber];
		for (int i = 0; i < parametersNumber; i++) {
			arguments[i] = parameters.get(i).getName();
		}

		// creating, setting up and executing internal query
		ProcedureQuery dbProcedureQuery = database.createProcedureQuery(procedureName, arguments);
		dbProcedureQuery.setFirstSolution(firstResult).setMaxSolution(maxResult).execute();
		hasMoreResult = dbProcedureQuery.hasNext();

		// setting parameters values
		for (int i = 0; i < parametersNumber; i++) {
			String parameterName = parameters.get(i).getName();
			Parameter<?> parameter = getParameter(parameterName);
			if (parameter instanceof JPAParameter) {
				Object parameterValue = dbProcedureQuery.getArgumentValue(parameterName);
				JPAParameter<?> logicParameter = (JPAParameter<?>) parameter;
				logicParameter.setValue(parameterValue);
			}
		}

		// adding in tuple list all results
		for (Iterator<?> e = dbProcedureQuery; e.hasNext();) {
			Object result = e.next();
			if (result instanceof Object[]) {
				Object[] objects = (Object[]) result;
				JPATuple tuple = new JPATuple(objects.length);

				// loop to fill each result tuple
				for (int i = 0; i < objects.length; i++) {
					Parameter<?> parameter = parameters.get(i);
					if (parameter instanceof JPAParameter) {
						JPAParameter<?> logicParameter = (JPAParameter<?>) parameter;
						ParameterMode mode = logicParameter.getParameterMode();
						if (mode == ParameterMode.INOUT || mode == ParameterMode.OUT) {
							String parameterName = logicParameter.getName();
							Object parameterValue = dbProcedureQuery.getArgumentValue(parameterName);
							JPATupleElement<?> element = new JPATupleElement(parameterName, Object.class,
									parameterValue);
							tuple.add(element);
						}
					}
				}
				tuples.add(tuple);
			}
		}

		return hasMoreResult;
	}

	public boolean hasMoreResults() {
		return hasMoreResult;
	}

	public int getUpdateCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T> StoredProcedureQuery setParameter(Parameter<T> param, T value) {
		if (param instanceof JPAParameter) {
			JPAParameter<T> parameter = (JPAParameter<T>) param;
			parameter.setValue(value);
			parameters.add(param);
		}
		return this;
	}

	public StoredProcedureQuery setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(Parameter<Calendar>, Calendar, TemporalType");
	}

	public StoredProcedureQuery setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(Parameter<Date>, Date, TemporalType");
	}

	public StoredProcedureQuery setParameter(String name, Object value) {
		Class<?> valueType = value != null ? value.getClass() : null;
		parameters.add(new JPAParameter(name, parameterPosition++, valueType, value));
		return this;
	}

	public StoredProcedureQuery setParameter(String name, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(String, Calendar, TemporalType");
	}

	public StoredProcedureQuery setParameter(String name, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(String, Date, TemporalType");
	}

	public StoredProcedureQuery setParameter(int position, Object value) {
		String name = WILD_CARD + String.valueOf(position);
		Class<?> valueType = value != null ? value.getClass() : null;
		parameters.add(new JPAParameter(name, position, valueType));
		return this;
	}

	public StoredProcedureQuery setParameter(int position, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(int, Calendar, TemporalType");
	}

	public StoredProcedureQuery setParameter(int position, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(int, Date, TemporalType)");
	}

	public StoredProcedureQuery setHint(String hintName, Object value) {
		hints.put(hintName, value);
		return this;
	}

	public StoredProcedureQuery setFlushMode(FlushModeType flushMode) {
		this.flushMode = flushMode;
		return this;
	}

}
