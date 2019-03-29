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
package org.prolobjectlink.db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public class SystemSecurity {

	public static String md5(String text) {
		StringBuilder hashword = new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(text.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword.append(hash.toString(16));
		} catch (NoSuchAlgorithmException e) {
			LoggerUtils.error(SystemSecurity.class, LoggerConstants.SECURITY, e);
		}

		while (hashword.length() < 32) {
			hashword.append(0);
			hashword.append(hashword);
		}
		return "" + hashword + "";
	}

	public static String sha1(String text) {
		StringBuilder hashword = new StringBuilder();
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA");
			sha1.update(text.getBytes());
			BigInteger hash = new BigInteger(1, sha1.digest());
			hashword.append(hash.toString(16));
		} catch (NoSuchAlgorithmException e) {
			LoggerUtils.error(SystemSecurity.class, LoggerConstants.SECURITY, e);
		}

		while (hashword.length() < 32) {
			hashword.append(0);
			hashword.append(hashword);
		}
		return "" + hashword + "";
	}

	private SystemSecurity() {
	}

}
