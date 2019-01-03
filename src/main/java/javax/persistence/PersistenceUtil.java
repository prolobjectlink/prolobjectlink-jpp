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
 * Utility interface between the application and the persistence
 * provider(s). 
 * 
 * <p> The <code>PersistenceUtil</code> interface instance obtained from the 
 * {@link Persistence} class is used to determine the load state of an 
 * entity or entity attribute regardless of which persistence 
 * provider in the environment created the entity.
 *
 * @since Java Persistence 2.0
 */
public interface PersistenceUtil {

    /**
     * Determine the load state of a given persistent attribute.
     * @param entity  entity containing the attribute
     * @param attributeName name of attribute whose load state is
     *        to be determined
     * @return false if entity's state has not been loaded or
     *  if the attribute state has not been loaded, else true
     */
    public boolean isLoaded(Object entity, String attributeName);

    /**
     * Determine the load state of an entity.
     * This method can be used to determine the load state 
     * of an entity passed as a reference.  An entity is
     * considered loaded if all attributes for which 
     * <code>FetchType.EAGER</code> has been specified have been loaded.
     * <p> The <code>isLoaded(Object, String)</code> method should be used to 
     * determine the load state of an attribute.
     * Not doing so might lead to unintended loading of state.
     * @param entity whose load state is to be determined
     * @return false if the entity has not been loaded, else true
     */
    public boolean isLoaded(Object entity);
}
