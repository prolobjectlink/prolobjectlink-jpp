/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.logicware.db.util;

import java.util.Random;

public class JavaStrings {

	private static final Random r = new Random();
	private static final String ALPHANUM =

			"abcdefghijklmnopqrstuvwxyz"

					+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

					+ "1234567890";

	public static String randomCharString32() {
		return randomCharString(32);
	}

	public static String randomCharString64() {
		return randomCharString(64);
	}

	public static String randomCharString(int length) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int n = r.nextInt(ALPHANUM.length());
			int number = n - 1 == -1 ? n : n - 1;
			char ch = ALPHANUM.charAt(number);
			b.append(ch);
		}
		return "" + b + "";
	}

	public static String toUpperCase(String target) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < target.length(); i++) {
			Character c = target.charAt(i);
			b.append(Character.toUpperCase(c));
		}
		return "" + b + "";
	}

	public static String toLowerCase(String target) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < target.length(); i++) {
			Character c = target.charAt(i);
			b.append(Character.toLowerCase(c));
		}
		return "" + b + "";
	}

	private JavaStrings() {
	}

}
