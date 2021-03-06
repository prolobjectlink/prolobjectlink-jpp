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
package io.github.prolobjectlink.db.prolog;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import io.github.prolobjectlink.db.TypedQuery;
import io.github.prolobjectlink.db.container.AbstractContainerQuery;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;

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
