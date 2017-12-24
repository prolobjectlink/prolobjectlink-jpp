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
package org.logicware.jpd.db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Security {

	private int i;
	private int j;
	private final byte[] sbox = new byte[256];

	public static final String SALT = "Prolobjectlink-Security";

	/**
	 * Generates a string of 32 letters (A to Z)
	 * 
	 * @return A string formed by 32 A-to-Z random letters.
	 */
	public static String randomCharString() {
		byte[] sbox = new byte[256];
		byte[] chars = new byte[32];
		for (int i = 0; i < 256; i++) {
			sbox[i] = (byte) i;
		}
		for (int i = 0; i < 32; i++) {
			int a = 65;
			int b = 83;
			double k = Math.random();
			int f = (int) (k * (b - a) + a);
			chars[i] = sbox[f];
		}
		return new String(chars);
	}

	/**
	 * Convert any String into an Hex equivalent; for example, "AtoZ" becomes
	 * "41746f5a". This is intended to produce a legible, pure ASCII, equivalent
	 * of the original string.
	 * 
	 * @param s
	 *            String to be converted
	 * @return Hex equivalent of the input string
	 */
	public static String byteStringToHexString(final String s) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			r.append(byteToHexChars(s.charAt(i)));
		}
		return r.toString();
	}

	/**
	 * Convert a number (0..255) into its two-character equivalent. For example,
	 * 15 returns "0F" and 100 returns "64".
	 * 
	 * @param i
	 *            Number to be converted
	 * @return Hex equivalent, in two characters.
	 */
	public static String byteToHexChars(final int i) {
		final String s = "0" + Integer.toHexString(i);
		return s.substring(s.length() - 2);
	}

	/**
	 * Revert the effect of byteStringToHexString(...); given an Hex equivalent
	 * string such as "41746f5a", produce the original "AtoZ".
	 * 
	 * @param s
	 *            Hex string to be converted
	 * @return Original string
	 */
	public static String hexStringToByteString(final String s) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < s.length(); i += 2) {
			r.append((char) Integer.parseInt(s.substring(i, i + 2), 16));
		}
		return r.toString();
	}

	public static String md5(final String text) {
		StringBuilder hashword = new StringBuilder();
		try {
			final MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(text.getBytes());
			final BigInteger hash = new BigInteger(1, md5.digest());
			hashword.append(hash.toString(16));
		} catch (final NoSuchAlgorithmException nsae) {
		}

		while (hashword.length() < 32) {
			hashword.append("0" + hashword);
		}
		return hashword.toString();
	}

	public static String sha1(final String text) {
		StringBuilder hashword = new StringBuilder();
		try {
			final MessageDigest sha1 = MessageDigest.getInstance("SHA");
			sha1.update(text.getBytes());
			final BigInteger hash = new BigInteger(1, sha1.digest());
			hashword.append(hash.toString(16));
		} catch (final NoSuchAlgorithmException nsae) {
		}

		while (hashword.length() < 32) {
			hashword.append("0" + hashword);
		}
		return hashword.toString();
	}

	/**
	 * Assuming everything was set up earlier, encode plaintext. This can be
	 * done in stream fashion; sequential calls to this routine will be the same
	 * as a single call with a longer parameter. In other words, as Benny Hill
	 * had it, codeDecode("THE")+codeDecode("RAPIST") equals
	 * codeDecode("THERAPIST")
	 * 
	 * @param plaintext
	 *            Text to encode
	 * @return Encoded equivalent
	 */
	public String codeDecode(final String plaintext) {
		byte x;
		String r = "";
		final int pl = plaintext.length();
		for (int k = 0; k < pl; k++) {
			i = i + 1 & 0xff;
			j = j + sbox[i] & 0xff;

			x = sbox[i];
			sbox[i] = sbox[j];
			sbox[j] = x;

			r += (char) (plaintext.charAt(k) ^ sbox[sbox[i] + sbox[j] & 0xff] & 0xff);
		}
		return r;
	}

	/**
	 * Set up the key, and use it to encode plaintext
	 * 
	 * @param key
	 * @param plaintext
	 * @return
	 */
	public String codeDecode(final String key, final String plaintext) {

		setUp(key);
		return codeDecode(plaintext);
	}

	/**
	 * Set up the internal parameters (sbox, i, j) so we can start decoding
	 * right away
	 * 
	 * @param key
	 */
	public void setUp(final String key) {
		int k;
		byte x;

		for (i = 0; i < 256; i++) {
			sbox[i] = (byte) i;
		}

		final int kl = key.length();
		for (i = 0, j = 0, k = 0; i < 256; i++) {
			j = j + sbox[i] + key.charAt(k) & 0xff;
			k = (k + 1) % kl;

			x = sbox[i];
			sbox[i] = sbox[j];
			sbox[j] = x;
		}

		// Set things up to start coding/decoding

		i = 0;
		j = 0;
	}
}
