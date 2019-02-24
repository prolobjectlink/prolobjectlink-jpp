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
