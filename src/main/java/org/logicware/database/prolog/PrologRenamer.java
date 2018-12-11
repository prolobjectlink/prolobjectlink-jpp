/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database.prolog;

import java.lang.reflect.Field;

import org.logicware.database.Renamer;
import org.logicware.database.util.Assertions;
import org.logicware.database.util.JavaReflect;
import org.logicware.database.util.JavaStrings;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologVariable;

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
