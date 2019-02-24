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

import org.junit.Test;

public class GraphEdgeTest extends BaseTest {

	@Test
	public void testGetFrom() {

		assertEquals(v1, e1.getFrom());
		assertEquals(v1, e2.getFrom());
		assertEquals(v1, e3.getFrom());
		assertEquals(v2, e4.getFrom());
		assertEquals(v2, e5.getFrom());
		assertEquals(v2, e6.getFrom());

	}

	@Test
	public void testGetTo() {

		assertEquals(v2, e1.getTo());
		assertEquals(v3, e2.getTo());
		assertEquals(v3, e4.getTo());
		assertEquals(v4, e5.getTo());
		assertEquals(v5, e3.getTo());
		assertEquals(v5, e6.getTo());

	}

	@Test
	public void testGetElement() {

		assertEquals("E1", e1.getElement());
		assertEquals("E2", e2.getElement());
		assertEquals("E3", e3.getElement());
		assertEquals("E4", e4.getElement());
		assertEquals("E5", e5.getElement());
		assertEquals("E6", e6.getElement());

	}

	@Test
	public void testGetElementClass() {

		assertEquals(String.class, e1.getElementClass());
		assertEquals(String.class, e2.getElementClass());
		assertEquals(String.class, e3.getElementClass());
		assertEquals(String.class, e4.getElementClass());
		assertEquals(String.class, e5.getElementClass());
		assertEquals(String.class, e6.getElementClass());

	}

	@Test
	public void testGetFromVertexElementClass() {

		assertEquals(String.class, e1.getFromVertexElementClass());
		assertEquals(String.class, e2.getFromVertexElementClass());
		assertEquals(String.class, e3.getFromVertexElementClass());
		assertEquals(String.class, e4.getFromVertexElementClass());
		assertEquals(String.class, e5.getFromVertexElementClass());
		assertEquals(String.class, e6.getFromVertexElementClass());

	}

	@Test
	public void testGetToVertexElementClass() {

		assertEquals(String.class, e1.getToVertexElementClass());
		assertEquals(String.class, e2.getToVertexElementClass());
		assertEquals(String.class, e3.getToVertexElementClass());
		assertEquals(String.class, e4.getToVertexElementClass());
		assertEquals(String.class, e5.getToVertexElementClass());
		assertEquals(String.class, e6.getToVertexElementClass());

	}

	@Test
	public void testGoString() {

		assertEquals("E1", e1.toString());

	}

}
