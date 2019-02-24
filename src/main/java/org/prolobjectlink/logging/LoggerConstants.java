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
package org.prolobjectlink.logging;

public enum LoggerConstants {

	RUNTIME_ERROR("Runtime error "),

	FILE_NOT_FOUND("File not found "),

	CLASS_NOT_FOUND("Class not found "),

	UNKNOW_PREDICATE("Unknow predicate"),

	SYNTAX_ERROR("Syntax error in the file "),

	NON_SOLUTION("The query no have solution "),

	INDICATOR_NOT_FOUND("Predicate not found for"),

	IO("Some error occurs opening the file"),

	ERROR_LOADING_BUILT_INS("Error loading prolog built-ins "),

	DONT_WORRY("Don't worry about it, the file was create for you "),

	INTERRUPTED_ERROR("Thread interrupted error"),

	EXECUTION_ERROR("Thread execution error"),

	FILE_NOT_DELETE("File not delete "),

	INSTANTIATION("Instantiation error "),

	ILLEGAL_ACCESS("Illegal access error "),

	NO_SUCH_METHOD("No such method error"),

	SECURITY("Security error "),

	SQL_ERROR("SQL error "),

	UNKNOWN_HOST("Unknow Host error"),

	ILLEGAL_ARGUMENT("Illegal argument error"),

	INVOCATION_TARGET("Invocation target error"),

	NO_SUCH_FIELD("No such field error"),

	CLASS_CAST("Class cast error"),

	URI("URI Syntax error"),

	URL("URL Syntax error"),

	LINK("Link library error");

	private final String name;

	LoggerConstants(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

}
