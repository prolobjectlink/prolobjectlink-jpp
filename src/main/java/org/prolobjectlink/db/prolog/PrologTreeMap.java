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
package org.prolobjectlink.db.prolog;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

class PrologTreeMap<K extends Comparable<? super K>, V> extends AbstractMap<K, V> implements Map.Entry<K, V> {

	private static final long serialVersionUID = -2244940649007449299L;

	private PrologTreeMap<K, V> left;
	private K key;
	private V value;
	private PrologTreeMap<K, V> right;

	PrologTreeMap() {
	}

	PrologTreeMap(Map<K, V> map) {
		putAll(map);
	}

	PrologTreeMap(K key, V value) {
		this.key = key;
		this.value = value;
	}

	PrologTreeMap(K key, V value, PrologTreeMap<K, V> left, PrologTreeMap<K, V> right) {
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
		PrologTreeMap<?, ?> other = (PrologTreeMap<?, ?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	protected PrologTreeMap<K, V> copy() {
		return new PrologTreeMap<K, V>(key, value, left, right);
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

		PrologTreeMap<K, V> root = this;

		if (!isEmpty()) {

			V v = null;
			PrologTreeMap<K, V> entry = new PrologTreeMap<K, V>(key, value);
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
		PrologTreeMap<K, V> root = this;

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
			PrologTreeMap<K, V> parent = root;

			if (left != null) {

				PrologTreeMap<K, V> treeIter = left;
				PrologTreeMap<K, V> predeccessor = this;
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

				PrologTreeMap<K, V> treeIter = right;
				PrologTreeMap<K, V> successor = this;
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

	private int heigh(PrologTreeMap<K, V> tree) {
		return tree != null ? 1 + Math.max(heigh(tree.left), heigh(tree.right)) : 0;
	}

	protected PrologTreeMap<K, V> getEntry(Object key) {
		if (key instanceof Comparable) {
			Comparable<K> k = (Comparable<K>) key;
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

	private void rotateLeft(PrologTreeMap<K, V> root) {

		PrologTreeMap<K, V> toBeLeft = root.copy();

		PrologTreeMap<K, V> r = toBeLeft.right;
		toBeLeft.right = r.left;
		r.left = toBeLeft;

		root.key = r.key;
		root.value = r.value;
		root.left = toBeLeft;
		root.right = r.right;

	}

	private void rotateRight(PrologTreeMap<K, V> root) {

		PrologTreeMap<K, V> toBeRight = root.copy();

		PrologTreeMap<K, V> l = toBeRight.left;
		toBeRight.left = l.right;
		l.right = toBeRight;

		root.key = l.key;
		root.value = l.value;
		root.left = l.left;
		root.right = toBeRight;

	}

	private void fixAfterChange(PrologTreeMap<K, V> root) {

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
			return PrologTreeMap.this.size();
		}

	}

	private class ValueCollection extends AbstractCollection<V> {

		@Override
		public Iterator<V> iterator() {
			return new ValueSetIterator();
		}

		@Override
		public int size() {
			return PrologTreeMap.this.size();
		}

	}

	private class EntrySet extends AbstractSet<Entry<K, V>> {

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntrySetIterator();
		}

		@Override
		public int size() {
			return PrologTreeMap.this.size();
		}

	}

	private abstract class AbstractIterator {

		private PrologTreeMap<K, V> last;

		// check illegal state
		private boolean canRemove;

		private final PrologTreeMap<K, V> root;
		private final Deque<PrologTreeMap<K, V>> stack;

		public AbstractIterator() {
			stack = new ArrayDeque<PrologTreeMap<K, V>>();
			PrologTreeMap<K, V> ptr = root = PrologTreeMap.this;
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

			PrologTreeMap<K, V> ptr = root;
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
			PrologTreeMap<K, V> ptr = last.right;
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
