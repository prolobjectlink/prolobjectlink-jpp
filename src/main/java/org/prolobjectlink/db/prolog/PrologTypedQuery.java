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
package org.prolobjectlink.db.prolog;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.container.AbstractContainerQuery;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public final class PrologTypedQuery<T> extends AbstractContainerQuery implements TypedQuery<T> {

	private int index;

	private ArrayList<T> solution;

	private static final long serialVersionUID = 7235574065767960027L;

	private AscendantComparator ascendant = new AscendantComparator();
	private DescendantComparator descendant = new DescendantComparator();

	public PrologTypedQuery(List<T> solution) {
		this.solution = new ArrayList<T>(solution);
	}

	private final class AscendantComparator implements Comparator<T>, Serializable {

		private static final long serialVersionUID = 6808414314975528796L;

		public int compare(T o1, T o2) {
			if (o1.hashCode() < o2.hashCode()) {
				return -1;
			} else if (o1.hashCode() > o2.hashCode()) {
				return 1;
			}
			return 0;
		}

	}

	private final class DescendantComparator implements Comparator<T>, Serializable {

		private static final long serialVersionUID = -1841515805554965199L;

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
		checkSolutionAt(first, solution.size());
		this.firstSolution = first;
		return this;
	}

	public TypedQuery<T> setMaxSolution(int maxSolution) {
		this.maxSolution = maxSolution;
		return this;
	}

	public T getSolution() {
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

	public boolean hasNext() {
		return index < solution.size();
	}

	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return solution.get(index++);
	}

	public void remove() {
		// skip
		next();
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
				solutionList.add(JavaReflect.readValue(field, object));
			} catch (NoSuchFieldException e) {
				LoggerUtils.error(getClass(), LoggerConstants.NO_SUCH_FIELD, e);
			}
		}
		return new PrologTypedQuery<Object>(solutionList);
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
		PrologTypedQuery<?> other = (PrologTypedQuery<?>) obj;
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
