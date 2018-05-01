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
package org.logicware.logging;

public enum LoggerConstants {

	RUNTIME_ERROR("Runtime error "),

	FILE_NOT_FOUND("File not found "),

	CLASS_NOT_FOUND("Class not found "),

	UNKNOW_PREDICATE("Unknow predicate"),

	SYNTAX_ERROR("Syntax error in the file "),

	NON_SOLUTION("The query no have solution "),

	INDICATOR_NOT_FOUND("Predicate not found for"),

	IO_ERROR("Some error occurs opening the file"),

	ERROR_LOADING_BUILT_INS("Error loading prolog built-ins "),

	DONT_WORRY("Don't worry about it, the file was create for you "),

	INTERRUPTED_ERROR("Thread interrupted error"),

	EXECUTION_ERROR("Thread execution error"),

	FILE_NOT_DELETE("File not delete "),

	INSTANTIATION_ERROR("Instantiation error "),

	ILLEGAL_ACCESS_ERROR("Illegal access error "),

	NO_SUCH_METHOD_ERROR("No such method error"),

	SECURITY_ERROR("Security error "),

	SQL_ERROR("SQL error ");

	private final String name;

	LoggerConstants(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

}
