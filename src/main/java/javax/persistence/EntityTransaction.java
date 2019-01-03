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
 * Interface used to control transactions on resource-local entity
 * managers.  The {@link EntityManager#getTransaction
 * EntityManager.getTransaction()} method returns the
 * <code>EntityTransaction</code> interface.

 *
 * @since Java Persistence 1.0
 */
public interface EntityTransaction {

     /**
      * Start a resource transaction. 
      * @throws IllegalStateException if <code>isActive()</code> is true
      */
     public void begin();

     /**
      * Commit the current resource transaction, writing any 
      * unflushed changes to the database.  
      * @throws IllegalStateException if <code>isActive()</code> is false
      * @throws RollbackException if the commit fails
      */
     public void commit();

     /**
      * Roll back the current resource transaction. 
      * @throws IllegalStateException if <code>isActive()</code> is false
      * @throws PersistenceException if an unexpected error 
      *         condition is encountered
      */
     public void rollback();

     /**
      * Mark the current resource transaction so that the only 
      * possible outcome of the transaction is for the transaction 
      * to be rolled back. 
      * @throws IllegalStateException if <code>isActive()</code> is false
      */
     public void setRollbackOnly();

     /**
      * Determine whether the current resource transaction has been 
      * marked for rollback.
      * @return boolean indicating whether the transaction has been
      *         marked for rollback
      * @throws IllegalStateException if <code>isActive()</code> is false
      */
     public boolean getRollbackOnly();

     /**
      * Indicate whether a resource transaction is in progress.
      * @return boolean indicating whether transaction is
      *         in progress
      * @throws PersistenceException if an unexpected error 
      *         condition is encountered
      */
     public boolean isActive();
}
