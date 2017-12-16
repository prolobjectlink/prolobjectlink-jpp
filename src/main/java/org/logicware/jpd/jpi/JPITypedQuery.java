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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.logicware.jpd.AbstractQuery;
import org.logicware.jpd.NonSolutionError;
import org.logicware.jpd.TypedQuery;

public final class JPITypedQuery<T> extends AbstractQuery<T> implements TypedQuery<T> {

    private int index;

    /** query result list */
    private List<T> solution;

    private AscendantComparator ascendant = new AscendantComparator();
    private DescendantComparator descendant = new DescendantComparator();

    public JPITypedQuery(List<T> solution) {
	this.solution = solution;
    }

    private final class AscendantComparator implements Comparator<T> {

	public int compare(T o1, T o2) {
	    if (o1.hashCode() < o2.hashCode()) {
		return -1;
	    } else if (o1.hashCode() > o2.hashCode()) {
		return 1;
	    }
	    return 0;
	}

    }

    private final class DescendantComparator implements Comparator<T> {

	public int compare(Object o1, Object o2) {
	    if (o1.hashCode() < o2.hashCode()) {
		return 1;
	    } else if (o1.hashCode() > o2.hashCode()) {
		return -1;
	    }
	    return 0;
	}

    }

    public TypedQuery<T> setFirstSolution(int first) {
	checkNonNegativePosition(first);
	this.firstSolution = first;
	return this;
    }

    public TypedQuery<T> setMaxSolution(int maxSolution) {
	this.maxSolution = maxSolution;
	return this;
    }

    public T getSolution() throws NonSolutionError {
	checkEmptySolution(solution.size());
	checkSolutionAt(firstSolution, solution.size());
	return solution.get(firstSolution);
    }

    public List<T> getSolutions() {
	checkSolutionAt(firstSolution, solution.size());
	int size = maxSolution < solution.size() ? maxSolution : solution.size();
	List<T> solutionList = new ArrayList<T>(size);
	Iterator<T> iterator = solution.listIterator(firstSolution);
	for (int i = firstSolution; i < size && iterator.hasNext(); i++) {
	    solutionList.add(iterator.next());
	}
	return solutionList;
    }

    public boolean hasMoreElements() {
	return index < solution.size();
    }

    public T nextElement() {

	if (!hasMoreElements()) {
	    throw new NoSuchElementException();
	}

	return solution.get(index++);
    }

    public TypedQuery<T> orderAscending() {
	Collections.sort(solution, ascendant);
	return this;
    }

    public TypedQuery<T> orderDescending() {
	Collections.sort(solution, descendant);
	return this;
    }

    public TypedQuery<T> orderBy(Comparator<T> comparator) {
	Collections.sort(solution, comparator);
	return this;
    }

    public T max() {
	return max(ascendant);
    }

    public T max(Comparator<T> comparator) {
	return Collections.max(solution, comparator);
    }

    public T min() {
	return min(ascendant);
    }

    public T min(Comparator<T> comparator) {
	return Collections.min(solution, comparator);
    }

    public TypedQuery<Object> descend(String name) {
	checkSolutionAt(firstSolution, solution.size());
	int size = maxSolution < solution.size() ? maxSolution : solution.size();
	List<Object> solutionList = new ArrayList<Object>(size);
	for (Object object : solution) {
	    Class<?> clazz = object.getClass();
	    try {
		Field field = clazz.getDeclaredField(name);
		solutionList.add(reflector.readValue(field, object));
	    } catch (NoSuchFieldException e) {
		throw EXCEPTIONS.noSuchFieldException(name, clazz, e);
	    }
	}
	return new JPITypedQuery<Object>(solutionList);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + index;
	result = prime * result + ((solution == null) ? 0 : solution.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	JPITypedQuery other = (JPITypedQuery) obj;
	if (index != other.index)
	    return false;
	if (solution == null) {
	    if (other.solution != null)
		return false;
	} else if (!solution.equals(other.solution))
	    return false;
	return true;
    }

    public int count() {
	return solution.size();
    }

    public void dispose() {
	firstSolution = 0;
	maxSolution = MAX;
	if (solution != null) {
	    solution.clear();
	    solution = null;
	}
    }

}
