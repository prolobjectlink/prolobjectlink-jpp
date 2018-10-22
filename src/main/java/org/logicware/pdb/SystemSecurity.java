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
package org.logicware.pdb;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.logicware.logging.LoggerConstants;
import org.logicware.logging.LoggerUtils;

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
