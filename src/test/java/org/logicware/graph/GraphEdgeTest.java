/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
