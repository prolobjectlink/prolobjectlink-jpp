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
package org.logicware.pdb;

/**
 * Operation code type to be interpreted by the remote server when this received
 * a request. Support all database operations and the additional no operation
 * (NOOP) operation type. NOOP is use when the active request is initialized and
 * replaced when another operation type is send to server.
 * 
 * @author Jose Zalacain
 * @since 1.0
 *
 */
public enum OperationType {

	NOOP,

	CONNECT, CLOSE,

	CREATE, DROP,

	INSERT, UPDATE, DELETE_CLASS, DELETE_ARRAY, CLEAR,

	CONSTAINS_STRING, CONSTAINS_OBJECT, CONSTAINS_CLASS, CONSTAINS_PREDICATE, CONSTAINS_INDICATOR,

	QUERY_STRING, QUERY_OBJECT, QUERY_CLASS, QUERY_PREDICATE,

	PROCEDURE,

	BEGIN, COMMIT, ROLLBACK,

	BACKUP, RESTORE,

	INCLUDE,

	DEFRAG;

}
