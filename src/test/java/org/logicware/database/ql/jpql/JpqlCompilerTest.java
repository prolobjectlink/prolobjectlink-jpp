/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.database.ql.jpql;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.logicware.database.querylang.jpql.JpqlCompiler;

public class JpqlCompilerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public final void testCompileJPABench1() {
		System.out
				.println(JpqlCompiler.treeNode(new HashSet<String>(), "SELECT o FROM Order o WHERE o.id >= :firstId"));
	}

	@Test
	@Ignore
	public final void testCompileJPABench2() {
		System.out.println(JpqlCompiler.treeNode(new HashSet<String>(), "SELECT COUNT(o) FROM Order o"));
	}

	@Test
	@Ignore
	public final void testCompileJPABench3() {
		System.out.println(JpqlCompiler.treeNode(new HashSet<String>(),
				"SELECT o FROM Order o WHERE MOD(o.id, :graphSize) = 1 AND o.id >= :firstId"));
	}

}
