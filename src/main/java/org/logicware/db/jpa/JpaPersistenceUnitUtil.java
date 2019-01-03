/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.logicware.db.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;

import org.logicware.db.util.JavaReflect;

public final class JpaPersistenceUnitUtil implements PersistenceUnitUtil {

	public JpaPersistenceUnitUtil() {
	}

	public boolean isLoaded(Object entity, String attributeName) {
		Class<?> clazz = entity.getClass();
		Field field = JavaReflect.getDeclaredField(clazz, attributeName);
		return JavaReflect.readValue(field, entity) != null;
	}

	public boolean isLoaded(Object entity) {
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!isLoaded(entity, field.getName())) {
				return false;
			}
		}
		return true;
	}

	public Object getIdentifier(Object entity) {
		Class<?> clazz = entity.getClass();

		// Check AccessType.PROPERTY
		if (clazz.isAnnotationPresent(Access.class)) {
			AccessType accessType = clazz.getAnnotation(Access.class).value();
			if (accessType == AccessType.PROPERTY) {
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(Id.class)) {
						return JavaReflect.invoke(entity, method);
					}
				}
			}
		}

		// AccessType.FIELD is assumed by default
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				return JavaReflect.readValue(field, entity);
			}
		}

		throw new PersistenceException(" The class " + clazz + " no field was annotated with @Id annotation");
	}

}
