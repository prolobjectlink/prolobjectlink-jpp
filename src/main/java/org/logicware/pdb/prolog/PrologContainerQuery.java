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
package org.logicware.pdb.prolog;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.logicware.logging.LoggerConstants;
import org.logicware.logging.LoggerUtils;
import org.logicware.pdb.Query;
import org.logicware.pdb.container.AbstractContainerQuery;
import org.logicware.pdb.util.JavaReflect;

public class PrologContainerQuery extends AbstractContainerQuery implements Query {

	private int index;

	private ArrayList<Object> solution;

	private AscendantComparator ascendant = new AscendantComparator();
	private DescendantComparator descendant = new DescendantComparator();

	private static final long serialVersionUID = 5670102326238421771L;

	private final class AscendantComparator implements Comparator<Object>, Serializable {

		private static final long serialVersionUID = -6327021886772541537L;

		public int compare(Object o1, Object o2) {

			if (o1 instanceof Object[] && o2 instanceof Object[]) {

				Object[] objects1 = (Object[]) o1;
				Object[] objects2 = (Object[]) o2;

				// comparison by length
				if (objects1.length < objects2.length) {
					return -1;
				} else if (objects1.length > objects2.length) {
					return 1;
				}

				for (int i = 0; i < objects1.length; i++) {

					Object object1 = objects1[i];
					Object object2 = objects2[i];

					if (object1 instanceof Object[] && object2 instanceof Object[]) {
						return compare(object1, object2);
					} else if (object1.hashCode() < object2.hashCode()) {
						return -1;
					} else if (object1.hashCode() > object2.hashCode()) {
						return 1;
					}

				}

			}

			return 0;

		}

	}

	private final class DescendantComparator implements Comparator<Object>, Serializable {

		private static final long serialVersionUID = -4336563430577273554L;

		public int compare(Object o1, Object o2) {

			if (o1 instanceof Object[] && o2 instanceof Object[]) {

				Object[] objects1 = (Object[]) o1;
				Object[] objects2 = (Object[]) o2;

				// comparison by length
				if (objects1.length < objects2.length) {
					return 1;
				} else if (objects1.length > objects2.length) {
					return -1;
				}

				for (int i = 0; i < objects1.length; i++) {

					Object object1 = objects1[i];
					Object object2 = objects2[i];

					if (object1 instanceof Object[] && object2 instanceof Object[]) {
						return compare(object1, object2);
					} else if (object1.hashCode() < object2.hashCode()) {
						return 1;
					} else if (object1.hashCode() > object2.hashCode()) {
						return -1;
					}

				}

			}

			return 0;

		}

	}

	public PrologContainerQuery(List<Object> solution) {
		this.solution = new ArrayList<Object>(solution);
	}

	public Query setFirstSolution(int first) {
		checkSolutionAt(first, solution.size());
		this.firstSolution = first;
		return this;
	}

	public Query setMaxSolution(int maxSolution) {
		this.maxSolution = maxSolution;
		return this;
	}

	public Object getSolution() {
		checkSolutionAt(firstSolution, solution.size());
		return solution.get(firstSolution);
	}

	public List<Object> getSolutions() {
		checkSolutionAt(firstSolution, solution.size());
		int size = maxSolution < solution.size() ? maxSolution : solution.size();
		List<Object> solutionList = new ArrayList<Object>(size);
		Iterator<Object> iterator = solution.listIterator(firstSolution);
		for (int i = firstSolution; i < size && iterator.hasNext(); i++) {
			solutionList.add(iterator.next());
		}
		return solutionList;
	}

	public boolean hasNext() {
		return index < solution.size();
	}

	public Object next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return solution.get(index++);
	}

	@Override
	public void remove() {
		// skip
		next();
	}

	public Query orderAscending() {
		Collections.sort(solution, ascendant);
		return this;
	}

	public Query orderDescending() {
		Collections.sort(solution, descendant);
		return this;
	}

	public Query orderBy(Comparator<Object> comparator) {
		Collections.sort(solution, comparator);
		return this;
	}

	public Object max() {
		return max(ascendant);
	}

	public Object max(Comparator<Object> comparator) {
		return Collections.max(solution, comparator);
	}

	public Object min() {
		return min(ascendant);
	}

	public Object min(Comparator<Object> comparator) {
		return Collections.min(solution, comparator);
	}

	public Query descend(String name) {
		checkSolutionAt(firstSolution, solution.size());
		int size = maxSolution < solution.size() ? maxSolution : solution.size();
		List<Object> solutionList = new ArrayList<Object>(size);
		for (Object object : solution) {
			if (object instanceof Object[]) {
				Object[] array = (Object[]) object;
				for (int i = 0; i < array.length; i++) {
					Object objectInArray = array[i];
					Class<?> clazz = objectInArray.getClass();
					try {
						Field field = clazz.getDeclaredField(name);
						array[i] = JavaReflect.readValue(field, objectInArray);
					} catch (NoSuchFieldException e) {
						LoggerUtils.error(getClass(), LoggerConstants.NO_SUCH_FIELD, e);
					}
				}
				solutionList.add(array);
			}
		}
		return new PrologContainerQuery(solutionList);
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
		PrologContainerQuery other = (PrologContainerQuery) obj;
		if (index != other.index)
			return false;
		if (solution == null) {
			if (other.solution != null)
				return false;
		} else if (!solution.equals(other.solution))
			return false;
		return true;
	}

	public void dispose() {
		firstSolution = 0;
		maxSolution = MAX;
		if (solution != null) {
			solution.clear();
			solution = null;
		}
	}

	public int count() {
		return solution.size();
	}

}
