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
package org.logicware.jpi;

import java.util.LinkedList;

public abstract class AbstractClauses extends LinkedList<PrologClause> implements PrologClauses {

	private static final long serialVersionUID = 2396826864308114598L;

	public void markDynamic() {
		// TODO Auto-generated method stub

	}

	public void unmarkDynamic() {
		// TODO Auto-generated method stub

	}

	public boolean isDynamic() {
		// TODO Auto-generated method stub
		return false;
	}

	public void markMultifile() {
		// TODO Auto-generated method stub

	}

	public void unmarkMultifile() {
		// TODO Auto-generated method stub

	}

	public boolean isMultifile() {
		// TODO Auto-generated method stub
		return false;
	}

	public void markDiscontiguous() {
		// TODO Auto-generated method stub

	}

	public void unmarkDiscontiguous() {
		// TODO Auto-generated method stub

	}

	public boolean isDiscontiguous() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getIndicator() {
		// TODO Auto-generated method stub
		return null;
	}

}
