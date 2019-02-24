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
package org.logicware.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.prolobjectlink.Graph;
import org.prolobjectlink.GraphVertex;
import org.prolobjectlink.graph.DirectedGraph;

public class GraphVertexTest extends BaseTest {

	@Test
	public void testGetIncomings() {

		Iterator<GraphVertex<String>> i = v3.getIncomingsVertices().iterator();
		assertEquals(v1, i.next());
		assertEquals(v2, i.next());

		i = v4.getIncomingsVertices().iterator();
		assertEquals(v2, i.next());

		i = v5.getIncomingsVertices().iterator();
		assertEquals(v1, i.next());
		assertEquals(v2, i.next());

	}

	@Test
	public void testGetOutgoings() {

		Iterator<GraphVertex<String>> i = v1.getOutgoingsVertices().iterator();
		assertEquals(v2, i.next());
		assertEquals(v3, i.next());
		assertEquals(v5, i.next());
		assertFalse(i.hasNext());

		i = v2.getOutgoingsVertices().iterator();
		assertEquals(v3, i.next());
		assertEquals(v4, i.next());
		assertEquals(v5, i.next());
		assertFalse(i.hasNext());

	}

	@Test
	public void testCountIncomings() {

		assertEquals(0, v1.countIncomings());
		assertEquals(1, v2.countIncomings());
		assertEquals(2, v3.countIncomings());
		assertEquals(1, v4.countIncomings());
		assertEquals(2, v5.countIncomings());
	}

	@Test
	public void testCountOutgoings() {

		assertEquals(3, v1.countOutgoings());
		assertEquals(3, v2.countOutgoings());

	}

	@Test
	public void testGetElement() {

		assertEquals("V1", v1.getElement());
		assertEquals("V2", v2.getElement());
		assertEquals("V3", v3.getElement());
		assertEquals("V4", v4.getElement());
		assertEquals("V5", v5.getElement());

	}

	@Test
	public void testGetElementClass() {

		assertEquals(String.class, v1.getElementClass());
		assertEquals(String.class, v2.getElementClass());
		assertEquals(String.class, v3.getElementClass());
		assertEquals(String.class, v4.getElementClass());
		assertEquals(String.class, v5.getElementClass());

	}

	@Test
	public void testUnwrapClassOfK() {
		assertEquals(null, v1.unwrap(String.class));
	}

	@Test
	public void testToString() {
		assertEquals("V1", v1.toString());
	}

	@Test
	public void testHashCode() {

		Graph<String, String> graph = new DirectedGraph<String, String>();
		assertEquals(v1.hashCode(), graph.addVertex("V1").hashCode());
		assertEquals(graph.addVertex((String) null).hashCode(), graph.addVertex((String) null).hashCode());

	}

	@Test
	public void testEquals() {

		Graph<String, String> graph = new DirectedGraph<String, String>();
		assertTrue(v1.equals(graph.addVertex("V1")));
		assertFalse(v1.equals(new Object()));
		assertFalse(v1.equals(null));
		assertFalse(v1.equals(v2));

		assertTrue(graph.addVertex((String) null).equals(graph.addVertex((String) null)));
		assertFalse(graph.addVertex((String) null).equals(new Object()));
		assertFalse(graph.addVertex((String) null).equals(v2));

	}

}
