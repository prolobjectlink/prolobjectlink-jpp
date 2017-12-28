/*
 * #%L
 * prolobjectlink
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
package org.logicware.jpd.jpi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.logicware.jpd.AbstractQuery;
import org.logicware.jpd.ConstraintQuery;
import org.logicware.jpd.NonSolutionError;
import org.logicware.jpd.ObjectConverter;
import org.logicware.jpd.TypedQuery;
import org.logicware.jpi.PrologEngine;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologQuery;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.PrologVariable;

/**
 * Implementation of {@code CriteriaQuery} interface. Contains a Prolog query
 * builder for build prolog query to be solved. Finally through prolog query
 * builder the query is builded and executed and the solution is passed to a new
 * TypedQuery object.
 * 
 * @author Jose Zalacain
 * 
 * @param <O>
 *            class object that is root of the query and the type of solutions
 * @since 1.0
 */
public final class PrologConstraintQuery<O>
		/* TODO remove this extension declaration */extends AbstractQuery<O> implements ConstraintQuery<O> {

	// trace flag
	private boolean trace;

	// prolog factory for instantiate prolog terms
	private final PrologProvider provider;

	//
	private final PrologEngine engine;

	// builder to build resolved criteria query
	private StringBuilder builder = new StringBuilder();

	// object factory to create object or prolog terms
	private ObjectConverter<PrologTerm> factory;

	// variable index for for prevent clashes
	private int varIndex = 0;

	// all declared class in the query with root query class at first position
	private List<Class<?>> classes = new ArrayList<Class<?>>();

	// map for resolve indexed prolog variables for prevent clashes
	private Map<String, String> fieldVarMap = new HashMap<String, String>();
	private Map<String, String> varFieldMap = new HashMap<String, String>();

	private static final String COMMA = ",";
	private static final String SEMICOLON = ";";

	// Logger for trace query
	private static final Logger logger = Logger.getLogger(ConstraintQuery.class.getName());

	public PrologConstraintQuery(String path, PrologEngine engine, PrologProvider prolog, Class<O> clazz) {
		engine.consult(path);
		this.engine = engine;
		this.provider = prolog;
		this.classes.add(clazz);
		this.factory = new PrologObjectConverter(prolog);
		String functor = "'" + clazz.getName() + "'";
		Field[] fields = clazz.getDeclaredFields();
		PrologTerm[] arguments = new PrologTerm[fields.length];
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			PrologVariable variable = createPrologVariable(field.getName(), varIndex);
			arguments[j] = variable;
			fieldVarMap.put(field.getName(), variable.getName());
			varFieldMap.put(variable.getName(), field.getName());
		}
		varIndex++;
		builder.append(provider.newStructure(functor, arguments));
	}

	private void checkNumericValue(Object value) {
		if (!(value instanceof Number)) {
			throw EXCEPTIONS.illegalOperandException(value);
		}
	}

	private void checkDeclaredFiled(String field) {
		if (!fieldVarMap.containsKey(field)) {
			throw EXCEPTIONS.noSuchFieldException(field);
		}
	}

	private PrologTerm createFieldValueExpression(String field, String operator, Object value) {
		checkDeclaredFiled(field);
		String indexedField = fieldVarMap.get(field);
		PrologTerm left = createPrologVariable(indexedField);
		PrologTerm right = factory.toTerm(value);
		return provider.newExpression(left, operator, right);
	}

	private PrologTerm createFieldFieldExpression(String left, String operator, String right) {
		checkDeclaredFiled(left);
		checkDeclaredFiled(right);
		PrologVariable leftField = createPrologVariable(fieldVarMap.get(left));
		PrologVariable rightField = createPrologVariable(fieldVarMap.get(right));
		return provider.newExpression(leftField, operator, rightField);
	}

	private PrologVariable createPrologVariable(String name) {
		String varName = "" + Character.toUpperCase(name.charAt(0)) + "";
		return provider.newVariable(varName + name.substring(1));
	}

	private PrologVariable createPrologVariable(String name, int index) {
		String varName = "" + Character.toUpperCase(name.charAt(0)) + "";
		return provider.newVariable(varName + name.substring(1) + "_" + index);
	}

	private void appendConjunction(PrologTerm prologTerm) {
		builder.append(COMMA + " " + prologTerm);
	}

	private void appendDisjunction(PrologTerm prologTerm) {
		builder.append(SEMICOLON + " " + prologTerm);
	}

	/**
	 * Trace formulated query if trace flag is enable enabled
	 * 
	 * @param query
	 */
	private void traceFormulatedQuery(PrologQuery query) {
		if (trace) {
			logger.info(query.toString());
		}
	}

	public ConstraintQuery<O> setFirstSolution(int first) {
		checkNonNegativePosition(first);
		this.firstSolution = first;
		return this;
	}

	public ConstraintQuery<O> setMaxSolution(int maxSolution) {
		this.maxSolution = maxSolution;
		return this;
	}

	public ConstraintQuery<O> not(Class<?> clazz) {
		classes.add(clazz);
		String functor = "'" + clazz.getName() + "'";
		Field[] fields = clazz.getDeclaredFields();
		PrologTerm[] arguments = new PrologTerm[fields.length];
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			PrologVariable variable = createPrologVariable(field.getName(), varIndex);
			arguments[j] = variable;
			fieldVarMap.put(field.getName(), variable.getName());
			varFieldMap.put(variable.getName(), field.getName());
		}
		varIndex++;
		appendConjunction(provider.newStructure(functor, arguments));
		return this;
	}

	public ConstraintQuery<O> and(Class<?> clazz) {
		classes.add(clazz);
		String functor = "'" + clazz.getName() + "'";
		Field[] fields = clazz.getDeclaredFields();
		PrologTerm[] arguments = new PrologTerm[fields.length];
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			PrologVariable variable = createPrologVariable(field.getName(), varIndex);
			arguments[j] = variable;
			fieldVarMap.put(field.getName(), variable.getName());
			varFieldMap.put(variable.getName(), field.getName());
		}
		varIndex++;
		appendConjunction(provider.newStructure(functor, arguments));
		return this;
	}

	public ConstraintQuery<O> or(Class<?> clazz) {
		classes.add(clazz);
		String functor = "'" + clazz.getName() + "'";
		Field[] fields = clazz.getDeclaredFields();
		PrologTerm[] arguments = new PrologTerm[fields.length];
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			PrologVariable variable = createPrologVariable(field.getName(), varIndex);
			arguments[j] = variable;
			fieldVarMap.put(field.getName(), variable.getName());
			varFieldMap.put(variable.getName(), field.getName());
		}
		varIndex++;
		appendDisjunction(provider.newStructure(functor, arguments));
		return this;
	}

	// term comparison operators

	public ConstraintQuery<O> unify(String field, Object value) {
		appendConjunction(createFieldValueExpression(field, "=", value));
		return this;
	}

	public ConstraintQuery<O> notUnify(String field, Object value) {
		appendConjunction(createFieldValueExpression(field, "\\=", value));
		return this;
	}

	public ConstraintQuery<O> equivalent(String field, Object value) {
		appendConjunction(createFieldValueExpression(field, "==", value));
		return this;
	}

	public ConstraintQuery<O> notEquivalent(String field, Object value) {
		appendConjunction(createFieldValueExpression(field, "\\==", value));
		return this;
	}

	public ConstraintQuery<O> before(String field, Object value) {
		appendConjunction(createFieldValueExpression(field, "@<", value));
		return this;
	}

	public ConstraintQuery<O> after(String field, Object value) {
		appendConjunction(createFieldValueExpression(field, "@>", value));
		return this;
	}

	public ConstraintQuery<O> beforeEquals(String field, Object value) {
		appendConjunction(createFieldValueExpression(field, "@=<", value));
		return this;
	}

	public ConstraintQuery<O> afterEquals(String field, Object value) {
		appendConjunction(createFieldValueExpression(field, "@>=", value));
		return this;
	}

	// arithmetics expression operators

	public ConstraintQuery<O> equals(String field, Object value) {
		checkNumericValue(value);
		appendConjunction(createFieldValueExpression(field, "=:=", value));
		return this;
	}

	public ConstraintQuery<O> notEquals(String field, Object value) {
		checkNumericValue(value);
		appendConjunction(createFieldValueExpression(field, "=\\=", value));
		return this;
	}

	public ConstraintQuery<O> greater(String field, Object value) {
		checkNumericValue(value);
		appendConjunction(createFieldValueExpression(field, ">", value));
		return this;
	}

	public ConstraintQuery<O> less(String field, Object value) {
		checkNumericValue(value);
		appendConjunction(createFieldValueExpression(field, "<", value));
		return this;
	}

	public ConstraintQuery<O> greaterEquals(String field, Object value) {
		checkNumericValue(value);
		appendConjunction(createFieldValueExpression(field, ">=", value));
		return this;
	}

	public ConstraintQuery<O> lessEquals(String field, Object value) {
		checkNumericValue(value);
		appendConjunction(createFieldValueExpression(field, "=<", value));
		return this;
	}

	public ConstraintQuery<O> unifyField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "=", right));
		return this;
	}

	public ConstraintQuery<O> notUnifyField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "\\=", right));
		return this;
	}

	public ConstraintQuery<O> equivalentField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "==", right));
		return this;
	}

	public ConstraintQuery<O> notEquivalentField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "\\==", right));
		return this;
	}

	public ConstraintQuery<O> beforeField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "@<", right));
		return this;
	}

	public ConstraintQuery<O> afterField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "@>", right));
		return this;
	}

	public ConstraintQuery<O> beforeEqualsField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "@=<", right));
		return this;
	}

	public ConstraintQuery<O> afterEqualsField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "@>=", right));
		return this;
	}

	// criteria for two declared fields

	public ConstraintQuery<O> equalsField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "=:=", right));
		return this;
	}

	public ConstraintQuery<O> notEqualsField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "=\\=", right));
		return this;
	}

	public ConstraintQuery<O> lessField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "<", right));
		return this;
	}

	public ConstraintQuery<O> greaterField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, ">", right));
		return this;
	}

	public ConstraintQuery<O> greaterEqualsField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, ">=", right));
		return this;
	}

	public ConstraintQuery<O> lessEqualsField(String left, String right) {
		appendConjunction(createFieldFieldExpression(left, "=<", right));
		return this;
	}

	public O getSolution() throws NonSolutionError {
		return createQuery().getSolution();
	}

	public List<O> getSolutions() {
		return createQuery().getSolutions();
	}

	public TypedQuery<O> createQuery() {
		List<O> solutionSet = new ArrayList<O>();
		String stringQuery = builder.toString();
		PrologQuery query = engine.query(stringQuery);
		traceFormulatedQuery(query);
		if (query.hasSolution()) {
			Map<String, PrologTerm>[] solutionsMap = query.allVariablesSolutions();
			for (int i = 0; i < solutionsMap.length; i++) {
				Map<String, PrologTerm> solution = solutionsMap[i];
				Map<String, PrologTerm> s = new HashMap<String, PrologTerm>(solution.size());
				Set<String> keys = solution.keySet();
				for (String key : keys) {
					PrologTerm term = solution.get(key);
					PrologVariable variable = createPrologVariable(varFieldMap.get(key));
					s.put(variable.getName(), term);
				}
				solutionSet.add((O) factory.toObject(classes.get(0), s));
			}
		}
		return new PrologTypedQuery<O>(solutionSet).setFirstSolution(firstSolution).setMaxSolution(maxSolution);
	}

	public ConstraintQuery<O> trace() {
		trace = true;
		return this;
	}

	public void dispose() {
		varIndex = 0;
		trace = false;
		builder = null;
		factory = null;
		if (classes != null) {
			classes.clear();
			classes = null;
		}
		if (varFieldMap != null) {
			varFieldMap.clear();
			varFieldMap = null;
		}
		if (fieldVarMap != null) {
			fieldVarMap.clear();
			fieldVarMap = null;
		}
	}

}
