/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.domain.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public final class Randomizer {

	private Randomizer() {

	}

	static Random random = new Random();

	// Random Strings:

	public static String randomFirstName() {
		return randomString(2, 12);
	}

	public static String randomMiddleName() {
		return randomString(2, 12);
	}

	public static String randomLastName() {
		return randomString(6, 12);
	}

	public static String randomStreet() {
		return randomString(10, 20);
	}

	public static String randomCity() {
		return randomString(10, 20);
	}

	public static String randomState() {
		return randomString(2, 2);
	}

	public static String randomCountry() {
		return randomString(3, 15);
	}

	public static String randomZip() {
		return random.nextBoolean() ? randomNumString(5, 5) : randomNumString(9, 9);
	}

	public static String randomPhone() {
		return randomNumString(15, 15);
	}

	public static String randomEmail() {
		return randomString(3, 8) + "@" + randomString(3, 8) + "." + randomString(2, 3);
	}

	public static String randomString(int minLength, int maxLength) {
		return randomString(randomInt(minLength, maxLength), 'A', 'Z');
	}

	public static String randomNumString(int minLength, int maxLength) {
		return randomString(randomInt(minLength, maxLength), '0', '9');
	}

	static String randomString(int length, char from, char to) {
		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			chars[i] = (char) randomInt(from, to);
		}
		return new String(chars);
	}

	// Random Dates:

	public static Date[] randomDates(int count) {
		Date[] dates = new Date[count];
		for (int i = 0; i < count; i++)
			dates[i] = randomDate();
		Arrays.sort(dates);
		return dates;
	}

	private static Date randomDate() {
		long time = (random.nextLong() & 0x1FFFFFFFFFFL) - 0xFFFFFFFFFFL;
		return new Date(time); // 1935 - 2004
	}

	// Random Numbers:

	public static int randomInt(int min, int max) {
		return max <= min ? min : (random.nextInt(max - min + 1) + min);
	}
}
