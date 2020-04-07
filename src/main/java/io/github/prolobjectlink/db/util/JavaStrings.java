/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db.util;

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
