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

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class TreeMap<K extends Comparable<? super K>, V> extends AbstractMap<K, V> implements Map.Entry<K, V> {

	private TreeMap<K, V> left;
	private K key;
	private V value;
	private TreeMap<K, V> right;

	public TreeMap() {
	}

	public TreeMap(Map<K, V> map) {
		putAll(map);
	}

	public TreeMap(K key, V value) {
		this.key = key;
		this.value = value;
	}

	TreeMap(K key, V value, TreeMap<K, V> left, TreeMap<K, V> right) {
		this(key, value);
		this.left = left;
		this.right = right;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		TreeMap<?, ?> other = (TreeMap<?, ?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	protected TreeMap<K, V> copy() {
		return new TreeMap<K, V>(key, value, left, right);
	}

	public int size() {
		if (!(key == null && value == null)) {
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

	public V put(K key, V value) {

		TreeMap<K, V> root = this;

		if (!isEmpty()) {

			V v = null;
			TreeMap<K, V> entry = new TreeMap<K, V>(key, value);
			if (key.compareTo(root.key) < 0) {
				if (root.left == null) {
					root.left = entry;
					v = entry.value;
				} else {
					v = root.left.put(key, value);
				}
			} else if (key.compareTo(root.key) > 0) {
				if (root.right == null) {
					root.right = entry;
					v = entry.value;
				} else {
					v = root.right.put(key, value);
				}
			} else {
				v = root.value = value;
			}

			// balance if required
			fixAfterChange(root);
			return v;
		}

		root.key = key;
		root.value = value;
		return value;

	}

	public V remove(Object key) {

		V v = null;
		K k = (K) key;
		TreeMap<K, V> root = this;

		if (k.compareTo(this.key) < 0) {
			if (left != null) {
				v = left.remove(k);
				if (left.isEmpty()) {
					left = null;
				}
			}
		} else if (k.compareTo(this.key) > 0) {
			if (right != null) {
				v = right.remove(k);
				if (right.isEmpty()) {
					right = null;
				}
			}
		} else {

			v = value;
			TreeMap<K, V> parent = root;

			if (left != null) {

				TreeMap<K, V> treeIter = left;
				TreeMap<K, V> predeccessor = this;
				while (treeIter != null) {
					parent = predeccessor;
					predeccessor = treeIter;
					treeIter = treeIter.right;
				}

				root.key = predeccessor.key;
				root.value = predeccessor.value;

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

				TreeMap<K, V> treeIter = right;
				TreeMap<K, V> successor = this;
				while (treeIter != null) {
					successor = treeIter;
					treeIter = treeIter.left;
				}

				root.key = successor.key;
				root.value = successor.value;

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
				this.key = null;
				this.value = null;

			}

		}

		// balance if required
		fixAfterChange(root);
		return v;
	}

	public void clear() {
		key = null;
		value = null;
		left = null;
		right = null;
	}

	public Set<K> keySet() {
		return new KeySet();
	}

	public Collection<V> values() {
		return new ValueCollection();
	}

	public Set<Entry<K, V>> entrySet() {
		return new EntrySet();
	}

	private int heigh(TreeMap<K, V> tree) {
		return tree != null ? 1 + Math.max(heigh(tree.left), heigh(tree.right)) : 0;
	}

	protected TreeMap<K, V> getEntry(Object key) {
		if (key instanceof Comparable) {
			K k = (K) key;
			if (k.compareTo(this.key) < 0) {
				if (left != null) {
					return left.getEntry(key);
				}
			} else if (k.compareTo(this.key) > 0 && right != null) {
				return right.getEntry(key);
			}
			return this;
		}
		return null;
	}

	private void rotateLeft(TreeMap<K, V> root) {

		TreeMap<K, V> toBeLeft = root.copy();

		TreeMap<K, V> r = toBeLeft.right;
		toBeLeft.right = r.left;
		r.left = toBeLeft;

		root.key = r.key;
		root.value = r.value;
		root.left = toBeLeft;
		root.right = r.right;

	}

	private void rotateRight(TreeMap<K, V> root) {

		TreeMap<K, V> toBeRight = root.copy();

		TreeMap<K, V> l = toBeRight.left;
		toBeRight.left = l.right;
		l.right = toBeRight;

		root.key = l.key;
		root.value = l.value;
		root.left = l.left;
		root.right = toBeRight;

	}

	private void fixAfterChange(TreeMap<K, V> root) {

		if (root != null) {

			int leftHeigh = heigh(root.left);
			int rightHeigh = heigh(root.right);

			if (leftHeigh - 2 == rightHeigh) {
				if (root.left != null) {

					leftHeigh = heigh(root.left.left);
					rightHeigh = heigh(root.left.right);

					if (leftHeigh < rightHeigh) {
						// rotate left -> root.left.key
						rotateLeft(root.left);
					}

				}
				// rotate right -> root.key
				rotateRight(root);
			} else if (leftHeigh == rightHeigh - 2) {
				if (root.right != null) {

					leftHeigh = heigh(root.right.left);
					rightHeigh = heigh(root.right.right);

					if (leftHeigh > rightHeigh) {
						// rotate right -> root.right.key
						rotateRight(root.right);
					}

				}
				// rotate left -> root.key
				rotateLeft(root);
			}

		}

	}

	private class KeySet extends AbstractSet<K> {

		@Override
		public Iterator<K> iterator() {
			return new KeySetIterator();
		}

		@Override
		public int size() {
			return TreeMap.this.size();
		}

	}

	private class ValueCollection extends AbstractCollection<V> {

		@Override
		public Iterator<V> iterator() {
			return new ValueSetIterator();
		}

		@Override
		public int size() {
			return TreeMap.this.size();
		}

	}

	private class EntrySet extends AbstractSet<Entry<K, V>> {

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntrySetIterator();
		}

		@Override
		public int size() {
			return TreeMap.this.size();
		}

	}

	private abstract class AbstractIterator {

		private TreeMap<K, V> last;

		// check illegal state
		private boolean canRemove;

		private final TreeMap<K, V> root;
		private final Deque<TreeMap<K, V>> stack;

		public AbstractIterator() {
			stack = new ArrayDeque<TreeMap<K, V>>();
			TreeMap<K, V> ptr = root = TreeMap.this;
			while (ptr != null && !ptr.isEmpty()) {
				stack.push(ptr);
				ptr = ptr.left;
			}
		}

		public boolean hasNext() {
			return !stack.isEmpty();
		}

		public void remove() {

			if (!canRemove) {
				throw new IllegalStateException();
			}

			stack.clear();

			TreeMap<K, V> ptr = root;
			ptr.remove(last.key);

			while (ptr != null && !ptr.isEmpty()) {
				stack.push(ptr);
				ptr = ptr.left;
			}

		}

		protected Entry<K, V> nextEntry() {

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			canRemove = true;

			last = stack.pop();
			TreeMap<K, V> ptr = last.right;
			while (ptr != null && !ptr.isEmpty()) {
				stack.push(ptr);
				ptr = ptr.left;
			}

			return last;
		}

	}

	private class KeySetIterator extends AbstractIterator implements Iterator<K> {

		public K next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return nextEntry().getKey();
		}

	}

	private class ValueSetIterator extends AbstractIterator implements Iterator<V> {

		public V next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return nextEntry().getValue();
		}

	}

	private class EntrySetIterator extends AbstractIterator implements Iterator<Entry<K, V>> {

		public Entry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return nextEntry();
		}

	}

}
