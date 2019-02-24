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
package org.prolobjectlink.db.prolog;

import java.lang.reflect.Field;

import org.prolobjectlink.db.Renamer;
import org.prolobjectlink.db.util.Assertions;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.db.util.JavaStrings;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologVariable;

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
