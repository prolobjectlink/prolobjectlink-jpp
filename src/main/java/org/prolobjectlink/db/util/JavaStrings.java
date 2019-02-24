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
package org.prolobjectlink.db.util;

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
