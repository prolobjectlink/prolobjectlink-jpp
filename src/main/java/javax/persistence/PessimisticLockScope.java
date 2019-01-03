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
 * Defines the values of the <code>javax.persistence.lock.scope</code>
 * property for pessimistic locking.  This property may be passed as
 * an argument to the methods of the {@link EntityManager},
 * {@link Query}, and {@link TypedQuery} interfaces that
 * allow lock modes to be specified or used with the
 * {@link NamedQuery} annotation.
 *
 * @since Java Persistence 2.0
 */
public enum PessimisticLockScope {

    /**
     * This value defines the default behavior for pessimistic locking.
     *
     * <p>The persistence provider must lock the database row(s) that
     * correspond to the non-collection-valued persistent state of
     * that instance.  If a joined inheritance strategy is used, or if
     * the entity is otherwise mapped to a secondary table, this
     * entails locking the row(s) for the entity instance in the
     * additional table(s).  Entity relationships for which the locked
     * entity contains the foreign key will also be locked, but not
     * the state of the referenced entities (unless those entities are
     * explicitly locked).  Element collections and relationships for
     * which the entity does not contain the foreign key (such as
     * relationships that are mapped to join tables or unidirectional
     * one-to-many relationships for which the target entity contains
     * the foreign key) will not be locked by default.
     */
    NORMAL,

    /**
     * In addition to the behavior for
     * <code>PessimisticLockScope.NORMAL</code>, element collections
     * and relationships owned by the entity that are contained in
     * join tables will be locked if the
     * <code>javax.persistence.lock.scope</code> property is specified
     * with a value of <code>PessimisticLockScope.EXTENDED</code>.
     * The state of entities referenced by such relationships will not
     * be locked (unless those entities are explicitly locked).
     * Locking such a relationship or element collection generally locks only
     * the rows in the join table or collection table for that
     * relationship or collection.  This means that phantoms will be
     * possible.
     */
    EXTENDED
}
