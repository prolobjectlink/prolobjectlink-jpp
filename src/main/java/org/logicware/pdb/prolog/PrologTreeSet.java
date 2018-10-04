/*
 * #%L
 * prolobjectlink-jpi
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

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.logicware.platform.Stack;
import org.logicware.platform.ArrayStack;

class PrologTreeSet<E extends Comparable<? super E>> extends AbstractSet<E> {

	private static final long serialVersionUID = 6033570255958454303L;

	private PrologTreeSet<E> left;
	private E element;
	private PrologTreeSet<E> right;

	PrologTreeSet() {
	}

	PrologTreeSet(E element) {
		this.element = element;
	}

	PrologTreeSet(Collection<? extends E> c) {
		addAll(c);
	}

	PrologTreeSet(PrologTreeSet<E> left, E element, PrologTreeSet<E> right) {
		this(element);
		this.left = left;
		this.right = right;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrologTreeSet<?> other = (PrologTreeSet<?>) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	public int size() {
		if (element != null) {
			int size = 1;
			if (left != null) {
				size += left.size();
			}
			if (right != null) {
				size += right.size();
			}
			return size;
		}
		return 0;
	}

	public boolean contains(Object o) {
		if (o instanceof Comparable) {
			E e = (E) o;
			if (e.compareTo(element) < 0) {
				if (left != null) {
					return left.contains(e);
				}
			} else if (e.compareTo(element) > 0 && right != null) {
				return right.contains(e);
			}
			return e.equals(element);
		}
		return false;
	}

	public boolean add(E e) {

		PrologTreeSet<E> root = this;

		boolean result = false;

		if (!isEmpty()) {

			if (e.compareTo(element) < 0) {
				if (root.left == null) {
					root.left = new PrologTreeSet<E>(e);
					result = true;
				} else {
					result = root.left.add(e);
				}
			} else if (e.compareTo(element) > 0) {
				if (root.right == null) {
					root.right = new PrologTreeSet<E>(e);
					result = true;
				} else {
					result = root.right.add(e);
				}

			}

			// balance if required
			fixAfterChange(root);
			return result;

		}

		root.element = e != null ? e : null;
		return e != null;
	}

	public boolean remove(Object o) {

		boolean isChanged = false;

		if (o instanceof Comparable) {

			PrologTreeSet<E> root = this;
			Comparable<E> e = (Comparable<E>) o;

			if (e.compareTo(this.element) < 0) {
				if (left != null) {
					isChanged = left.remove(e);
					if (left.isEmpty()) {
						left = null;
					}
				}
			} else if (e.compareTo(this.element) > 0) {
				if (right != null) {
					isChanged = right.remove(e);
					if (right.isEmpty()) {
						right = null;
					}
				}
			} else {

				PrologTreeSet<E> parent = root;

				if (left != null) {

					PrologTreeSet<E> treeIter = left;
					PrologTreeSet<E> predeccessor = this;
					while (treeIter != null) {
						parent = predeccessor;
						predeccessor = treeIter;
						treeIter = treeIter.right;
					}

					root.element = predeccessor.element;

					// two subtree children
					if (root.left != null && root.right != null) {
						// two subtree children
						if (root.left.equals(predeccessor)) {
							root.left = predeccessor.left;
						} else {
							parent.right = predeccessor.left;
						}

					} else {
						root.left = predeccessor.left;
						root.right = predeccessor.right;
					}

				} else if (right != null) {

					PrologTreeSet<E> treeIter = right;
					PrologTreeSet<E> successor = this;
					while (treeIter != null) {
						successor = treeIter;
						treeIter = treeIter.left;
					}

					root.element = successor.element;

					// two subtree children
					if (root.left != null && root.right != null) {
						// two subtree children
						if (root.right.equals(successor)) {
							root.left = successor.right;
						} else {
							parent.left = successor.right;
						}

					} else {
						root.left = successor.left;
						root.right = successor.right;
					}

				} else {

					// put to empty
					this.element = null;

				}

				// tree was changed
				isChanged = true;

			}

			// balance if required
			fixAfterChange(root);

		}

		return isChanged;
	}

	public void clear() {
		left = null;
		element = null;
		right = null;
	}

	public Iterator<E> iterator() {
		return new TreeSetIterator();
	}

	protected PrologTreeSet<E> copy() {
		return new PrologTreeSet<E>(left, element, right);
	}

	private int heigh(PrologTreeSet<E> tree) {
		return tree != null ? 1 + Math.max(heigh(tree.left), heigh(tree.right)) : 0;
	}

	private void rotateLeft(PrologTreeSet<E> root) {

		PrologTreeSet<E> toBeLeft = root.copy();

		PrologTreeSet<E> r = toBeLeft.right;
		toBeLeft.right = r.left;
		r.left = toBeLeft;

		root.element = r.element;
		root.left = toBeLeft;
		root.right = r.right;

	}

	private void rotateRight(PrologTreeSet<E> root) {

		PrologTreeSet<E> toBeRight = root.copy();

		PrologTreeSet<E> l = toBeRight.left;
		toBeRight.left = l.right;
		l.right = toBeRight;

		root.element = l.element;
		root.left = l.left;
		root.right = toBeRight;

	}

	private void fixAfterChange(PrologTreeSet<E> root) {

		if (root != null) {

			int leftHeigh = heigh(root.left);
			int rightHeigh = heigh(root.right);

			if (leftHeigh - 2 == rightHeigh) {
				if (root.left != null) {

					leftHeigh = heigh(root.left.left);
					rightHeigh = heigh(root.left.right);

					if (leftHeigh < rightHeigh) {
						// rotate left -> root.left.element
						rotateLeft(root.left);
					}

				}
				// rotate right -> root.element
				rotateRight(root);
			} else if (leftHeigh == rightHeigh - 2) {
				if (root.right != null) {

					leftHeigh = heigh(root.right.left);
					rightHeigh = heigh(root.right.right);

					if (leftHeigh > rightHeigh) {
						// rotate right -> root.right.element
						rotateRight(root.right);
					}

				}
				// rotate left -> root.element
				rotateLeft(root);
			}

		}

	}

	private class TreeSetIterator implements Iterator<E> {

		private PrologTreeSet<E> last;

		// check illegal state
		private boolean canRemove;

		private final PrologTreeSet<E> root;
		private final Stack<PrologTreeSet<E>> stack;

		public TreeSetIterator() {
			stack = new ArrayStack<PrologTreeSet<E>>();
			PrologTreeSet<E> ptr = root = PrologTreeSet.this;

			while (ptr != null && !ptr.isEmpty()) {
				stack.push(ptr);
				ptr = ptr.left;
			}
		}

		public boolean hasNext() {
			return !stack.isEmpty();
		}

		public E next() {

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			canRemove = true;

			last = stack.pop();
			PrologTreeSet<E> ptr = last.right;
			while (ptr != null && !ptr.isEmpty()) {
				stack.push(ptr);
				ptr = ptr.left;
			}

			return last.element;

		}

		public void remove() {

			if (!canRemove) {
				throw new IllegalStateException();
			}

			stack.clear();

			PrologTreeSet<E> ptr = root;
			ptr.remove(last.element);

			while (ptr != null && !ptr.isEmpty()) {
				stack.push(ptr);
				ptr = ptr.left;
			}

		}
	}

}
