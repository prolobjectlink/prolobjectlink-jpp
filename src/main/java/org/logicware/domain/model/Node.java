
package org.logicware.domain.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Node {

	@Id
	Integer id;

	@Basic
	int changeCount;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Node child1;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Node child2;

	public Node() {
	}

	public Node(int id) {
		this.id = Integer.valueOf(id);
	}

	public void setChild1(Node child1) {
		this.child1 = child1;
	}

	public Node getChild1() {
		return child1;
	}

	public void setChild2(Node child2) {
		this.child2 = child2;
	}

	public Node getChild2() {
		return child2;
	}

	public void load() {
		if (child1 != null) {
			child1.load();
		}
		if (child2 != null) {
			child2.load();
		}
	}

	public void update() {
		changeCount++;
		if (child1 != null) {
			child1.update();
		}
		if (child2 != null) {
			child2.update();
		}
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
