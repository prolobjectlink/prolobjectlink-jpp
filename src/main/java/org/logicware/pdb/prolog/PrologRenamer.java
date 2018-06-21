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
package org.logicware.pdb.prolog;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.logicware.pdb.util.JavaAsserts;
import org.logicware.pdb.util.JavaReflect;
import org.logicware.pdb.util.JavaStrings;

/**
 * Attribute renamer to prevent variable clashes in prolog queries. Basically
 * convert a class field name in one qualified prolog variable name. The
 * qualified prolog variable name respect the prolog variable syntax. Have
 * methods for convert from {@link Field} to {@link PrologVariable} and
 * vice-versa. E.g
 * 
 * 
 * from {@code org.logicware.domain.geometry.Point.idp} to
 * {@code ORG_LOGICWARE_DOMAIN_GEOMETRY_POINT_idp_0} from
 * {@code ORG_LOGICWARE_DOMAIN_GEOMETRY_POINT_idp_0 } to
 * {@code org.logicware.domain.geometry.Point.idp}
 * 
 * 
 * @author Jose Zalacain
 * @see #toField(PrologVariable)
 * @see #toVariable(Field)
 * @since 1.0
 */
final class PrologRenamer {

	// provider for variable creation
	private final PrologProvider provider;

	// map prolog renamed variable name to declared class
	private final Map<String, Class<?>> varMap;

	PrologRenamer(PrologProvider provider) {
		varMap = new HashMap<String, Class<?>>();
		this.provider = provider;
	}

	/**
	 * Convert from {@link Field} to {@link PrologVariable} e.g from
	 * {@code org.logicware.domain.geometry.Point.idp} to
	 * {@code ORG_LOGICWARE_DOMAIN_GEOMETRY_POINT_idp_0}
	 * 
	 * @param field to be converted
	 * @return prolog variable renamed that represent this field
	 * @since 1.0
	 */
	public final PrologVariable toVariable(Field field) {
		Field workField = JavaAsserts.requireNotNull(field);
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
		varMap.put(name, workClass);
		return provider.newVariable(name, index);
	}

	/**
	 * Convert from {@link PrologVariable} to {@link Field} e.g from
	 * {@code ORG_LOGICWARE_DOMAIN_GEOMETRY_POINT_idp_0 } to
	 * {@code org.logicware.domain.geometry.Point.idp}
	 * 
	 * 
	 * @param variable prolog variable to be converted
	 * @return equivalent filed to prolog variable.
	 * @since 1.0
	 */
	public final Field toField(PrologVariable variable) {
		return toField(variable.getName());
	}

	public final Field toField(String name) {
		String workName = JavaAsserts.requireNotNull(name);
		Class<?> workClass = varMap.get(workName);
		String message = name + "don't belong to any class field";
		workClass = JavaAsserts.notNull(workClass, message);
		String className = workClass.getName();
		className = className.replace('.', '_') + "_";
		String target = JavaStrings.toUpperCase(className);
		workName = workName.replace(target, "");
		int endIndex = workName.lastIndexOf('_');
		workName = workName.substring(0, endIndex);
		return JavaReflect.getDeclaredField(workClass, workName);
	}

	public final PrologProvider getProvider() {
		return provider;
	}

}
