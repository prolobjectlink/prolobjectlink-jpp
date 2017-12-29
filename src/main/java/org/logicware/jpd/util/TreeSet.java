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
package org.logicware.jpd.util;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeSet<E extends Comparable<? super E>> extends AbstractSet<E> {

	private TreeSet<E> left;
	private E element;
	private TreeSet<E> right;

	public TreeSet() {
	}

	TreeSet(E element) {
		this.element = element;
	}

	public TreeSet(Collection<? extends E> c) {
		addAll(c);
	}

	TreeSet(TreeSet<E> left, E element, TreeSet<E> right) {
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
		TreeSet<?> other = (TreeSet<?>) obj;
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

		TreeSet<E> root = this;

		boolean result = false;

		if (!isEmpty()) {

			if (e.compareTo(element) < 0) {
				if (root.left == null) {
					root.left = new TreeSet<E>(e);
					result = true;
				} else {
					result = root.left.add(e);
				}
			} else if (e.compareTo(element) > 0) {
				if (root.right == null) {
					root.right = new TreeSet<E>(e);
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

			E e = (E) o;
			TreeSet<E> root = this;

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

				TreeSet<E> parent = root;

				if (left != null) {

					TreeSet<E> treeIter = left;
					TreeSet<E> predeccessor = this;
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

					TreeSet<E> treeIter = right;
					TreeSet<E> successor = this;
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

	protected TreeSet<E> copy() {
		return new TreeSet<E>(left, element, right);
	}

	private int heigh(TreeSet<E> tree) {
		return tree != null ? 1 + Math.max(heigh(tree.left), heigh(tree.right)) : 0;
	}

	private void rotateLeft(TreeSet<E> root) {

		TreeSet<E> toBeLeft = root.copy();

		TreeSet<E> r = toBeLeft.right;
		toBeLeft.right = r.left;
		r.left = toBeLeft;

		root.element = r.element;
		root.left = toBeLeft;
		root.right = r.right;

	}

	private void rotateRight(TreeSet<E> root) {

		TreeSet<E> toBeRight = root.copy();

		TreeSet<E> l = toBeRight.left;
		toBeRight.left = l.right;
		l.right = toBeRight;

		root.element = l.element;
		root.left = l.left;
		root.right = toBeRight;

	}

	private void fixAfterChange(TreeSet<E> root) {

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

		private TreeSet<E> last;

		// check illegal state
		private boolean canRemove;

		private final TreeSet<E> root;
		private final Deque<TreeSet<E>> stack;

		public TreeSetIterator() {
			stack = new ArrayDeque<TreeSet<E>>();
			TreeSet<E> ptr = root = TreeSet.this;

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
			TreeSet<E> ptr = last.right;
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

			TreeSet<E> ptr = root;
			ptr.remove(last.element);

			while (ptr != null && !ptr.isEmpty()) {
				stack.push(ptr);
				ptr = ptr.left;
			}

		}
	}

}
