/*
 * #%L
 * prolobjectlink-db
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

package org.logicware.pdb.util;

public final class JavaAsserts {

	public static <T> T notNull(T object) {
		if (object == null) {
			throw new NullPointerException();
		}
		return object;
	}

	public static <T> T notNull(T object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
		return object;
	}

	public static <T> T requireNotNull(T object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		return object;
	}

	public static <T> T requireNotNull(T object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
		return object;
	}

	public static int checkIndex(int index, int size) {
		return checkIndex(index, size, "");
	}

	public static int checkIndex(int index, int size, String message) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(message);
		}
		return index;
	}

	private JavaAsserts() {
	}
}
