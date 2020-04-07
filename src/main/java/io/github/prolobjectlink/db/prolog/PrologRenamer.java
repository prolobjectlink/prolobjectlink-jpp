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
package io.github.prolobjectlink.db.prolog;

import java.lang.reflect.Field;

import io.github.prolobjectlink.db.Renamer;
import io.github.prolobjectlink.db.util.Assertions;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.db.util.JavaStrings;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologVariable;

final class PrologRenamer extends AbstractRenamer implements Renamer {

	PrologRenamer(PrologProvider provider) {
		super(provider);
	}

	public final PrologVariable toVariable(Field field) {
		Field workField = Assertions.requireNotNull(field);
		Class<?> workClass = workField.getDeclaringClass();
		String className = workClass.getName();
		String name = className.replace('.', '_');
		name = JavaStrings.toUpperCase(name);
		name += "_" + workField.getName();
		int index = -1;
		Field[] fields = workClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (field.equals(fields[i])) {
				index = i;
			}
		}
		name += "_" + index;
		getVariableMap().put(name, workClass);
		return getProvider().newVariable(name, index);
	}

	public final Field toField(PrologVariable variable) {
		return toField(variable.getName());
	}

	public final Field toField(String name) {
		String workName = Assertions.requireNotNull(name);
		Class<?> workClass = getVariableMap().get(workName);
		String message = name + "don't belong to any class field";
		workClass = Assertions.notNull(workClass, message);
		String className = workClass.getName();
		className = className.replace('.', '_') + "_";
		String target = JavaStrings.toUpperCase(className);
		workName = workName.replace(target, "");
		int endIndex = workName.lastIndexOf('_');
		workName = workName.substring(0, endIndex);
		return JavaReflect.getDeclaredField(workClass, workName);
	}

}
