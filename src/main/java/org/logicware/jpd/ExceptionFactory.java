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
package org.logicware.jpd;

import org.logicware.jpp.ClassNotFoundError;
import org.logicware.jpp.IllegalAccessError;
import org.logicware.jpp.InstantiationError;
import org.logicware.jpp.InvocationTargetError;
import org.logicware.jpp.NoSuchFieldError;
import org.logicware.jpp.RuntimeError;
import org.logicware.jpp.SecurityError;

/**
 * @deprecated throw exceptions directly
 * @author jose
 *
 */
@Deprecated
public final class ExceptionFactory {

	public BadPositionError badPositionException(int position) {
		return new BadPositionError(position);
	}

	public ClassNotFoundError classNotFoundException(String className) {
		return new ClassNotFoundError(className);
	}

	public ClassNotFoundError classNotFoundException(String className, int fields) {
		return new ClassNotFoundError(className, fields);
	}

	public ClassNotFoundError classNotFoundException(String className, Throwable throwable) {
		return new ClassNotFoundError(className, throwable);
	}

	public IllegalAccessError illegalAccessException(String className, Throwable throwable) {
		return new IllegalAccessError(className, throwable);
	}

	public IllegalOperandError illegalOperandException(Object value) {
		return new IllegalOperandError(value);
	}

	public InstantiationError instantiationException(String member) {
		return new InstantiationError(member);
	}

	public InstantiationError instantiationException(String member, Throwable throwable) {
		return new InstantiationError(member, throwable);
	}

	public InvocationTargetError invocationTargetException(String methodName) {
		return new InvocationTargetError(methodName);
	}

	public InvocationTargetError invocationTargetException(String methodName, Throwable throwable) {
		return new InvocationTargetError(methodName, throwable);
	}

	public RuntimeError newProlobjectlinkException(String message) {
		return new RuntimeError(message);
	}

	public NoSuchFieldError noSuchFieldException(String field) {
		return new NoSuchFieldError(field);
	}

	public NoSuchFieldError noSuchFieldException(String field, Class<?> clazz, Throwable throwable) {
		return new NoSuchFieldError(field, clazz, throwable);
	}

	public NonSolutionError nonSolutionException() {
		return new NonSolutionError();
	}

	public PersistenceError persistenceException(Object object) {
		return new PersistenceError(object);
	}

	public ProcedureInvokationError procedureInvokationException(String name, int expected, int invoked) {
		return new ProcedureInvokationError(name, expected, invoked);
	}

	public ProcedureArgumentError procedureArgumentException(String procedureName, String argumentName) {
		return new ProcedureArgumentError(procedureName, argumentName);
	}

	public SecurityError securityException(String className, Throwable throwable) {
		return new SecurityError(className, throwable);
	}

	public UpdateError updateException(Object match, Object update) {
		return new UpdateError(match, update);
	}

}
