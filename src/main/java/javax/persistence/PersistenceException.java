/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 WorkLogic Project
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
/*******************************************************************************
 * Copyright (c) 2008 - 2013 Oracle Corporation. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Linda DeMichiel - Java Persistence 2.1
 *     Linda DeMichiel - Java Persistence 2.0
 *
 ******************************************************************************/ 
package javax.persistence;


/**
 * Thrown by the persistence provider when a problem occurs.
 * All instances of <code>PersistenceException</code> except for instances of 
 * {@link NoResultException}, {@link NonUniqueResultException},
 * {@link LockTimeoutException}, and {@link QueryTimeoutException} will cause 
 * the current transaction, if one is active and the persistence context has
 * been joined to it, to be marked for rollback.
 *
 * @since Java Persistence 1.0
 */
public class PersistenceException extends RuntimeException {

        /** 
         * Constructs a new <code>PersistenceException</code> exception 
         * with <code>null</code> as its detail message.
         */
	public PersistenceException() {
		super();
	}

        /** 
         * Constructs a new <code>PersistenceException</code> exception 
         * with the specified detail message.
         * @param   message   the detail message.
         */
	public PersistenceException(String message) {
		super(message);
	}

        /** 
         * Constructs a new <code>PersistenceException</code> exception 
         * with the specified detail message and cause.
         * @param   message   the detail message.
         * @param   cause     the cause.
         */
	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}
	
        /** 
         * Constructs a new <code>PersistenceException</code> exception 
         * with the specified cause.
         * @param   cause     the cause.
         */
	public PersistenceException(Throwable cause) {
		super(cause);
	}
}

