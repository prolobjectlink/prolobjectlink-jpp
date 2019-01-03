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
package javax.persistence.metamodel;

/**
 * Instances of the type <code>SingularAttribute</code> represents persistent 
 * single-valued properties or fields.
 *
 * @param <X> The type containing the represented attribute
 * @param <T> The type of the represented attribute
 *
 * @since Java Persistence 2.0
 */
public interface SingularAttribute<X, T> 
		extends Attribute<X, T>, Bindable<T> {
	
    /**
     *  Is the attribute an id attribute.  This method will return
     *  true if the attribute is an attribute that corresponds to
     *  a simple id, an embedded id, or an attribute of an id class.
     *  @return boolean indicating whether the attribute is an id
     */
    boolean isId();

    /**
     *  Is the attribute a version attribute.
     *  @return boolean indicating whether the attribute is 
     *          a version attribute
     */
    boolean isVersion();

    /** 
     *  Can the attribute be null.
     *  @return boolean indicating whether the attribute can
     *          be null
     */
    boolean isOptional();

    /**
     * Return the type that represents the type of the attribute.
     * @return type of attribute
     */
    Type<T> getType();
}