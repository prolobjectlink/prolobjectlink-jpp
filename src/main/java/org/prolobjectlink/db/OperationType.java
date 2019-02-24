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
package org.prolobjectlink.db;

/**
 * Operation code type to be interpreted by the remote server when this received
 * a request. Support all database operations and the additional no operation
 * (NOOP) operation type. NOOP is use when the active request is initialized and
 * replaced when another operation type is send to server.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public enum OperationType {

	NOOP,

	CONNECT, CLOSE,

	EXIST, CREATE, DROP,

	INSERT, UPDATE, DELETE_CLASS, DELETE_ARRAY, CLEAR,

	CONSTAINS_STRING, CONSTAINS_OBJECT, CONSTAINS_CLASS, CONSTAINS_PREDICATE, CONSTAINS_INDICATOR,

	QUERY_STRING, QUERY_OBJECT, QUERY_CLASS, QUERY_PREDICATE,

	PROCEDURE,

	ACTIVE, BEGIN, COMMIT, ROLLBACK,

	BACKUP, RESTORE,

	INCLUDE,

	DEFRAG;

}
