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

import org.objectweb.asm.Opcodes;

public class ASMVersion {

	private ASMVersion() {
	}

	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public static int getCompatible() {
		String javaVersion = getJavaVersion();
		if (javaVersion.contains("1.1")) {
			return Opcodes.V1_1;
		} else if (javaVersion.contains("1.2")) {
			return Opcodes.V1_2;
		} else if (javaVersion.contains("1.3")) {
			return Opcodes.V1_3;
		} else if (javaVersion.contains("1.4")) {
			return Opcodes.V1_4;
		} else if (javaVersion.contains("1.5")) {
			return Opcodes.V1_5;
		} else if (javaVersion.contains("1.6")) {
			return Opcodes.V1_6;
		} else if (javaVersion.contains("1.7")) {
			return Opcodes.V1_7;
		} else if (javaVersion.contains("1.8")) {
			return Opcodes.V1_8;
		}
		// TODO exception here
		return Opcodes.NULL;
	}

}
