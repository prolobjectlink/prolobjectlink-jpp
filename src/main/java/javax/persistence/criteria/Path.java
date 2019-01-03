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
package javax.persistence.criteria;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.MapAttribute;

/**
 * Represents a simple or compound attribute path from a 
 * bound type or collection, and is a "primitive" expression.
 *
 * @param <X>  the type referenced by the path
 *
 * @since Java Persistence 2.0
 */
public interface Path<X> extends Expression<X> {

    /** 
     * Return the bindable object that corresponds to the
     * path expression.
     * @return bindable object corresponding to the path
     */
    Bindable<X> getModel(); 
    
    /**
     *  Return the parent "node" in the path or null if no parent.
     *  @return parent
     */
    Path<?> getParentPath();
	
    /**
     *  Create a path corresponding to the referenced 
     *  single-valued attribute.
     *  @param attribute single-valued attribute
     *  @return path corresponding to the referenced attribute
     */
    <Y> Path<Y> get(SingularAttribute<? super X, Y> attribute);

    /**
     *  Create a path corresponding to the referenced 
     *  collection-valued attribute.
     *  @param collection collection-valued attribute
     *  @return expression corresponding to the referenced attribute
     */
    <E, C extends java.util.Collection<E>> Expression<C> get(PluralAttribute<X, C, E> collection);

    /**
     *  Create a path corresponding to the referenced 
     *  map-valued attribute.
     *  @param map map-valued attribute
     *  @return expression corresponding to the referenced attribute
     */
    <K, V, M extends java.util.Map<K, V>> Expression<M> get(MapAttribute<X, K, V> map);

    /**
     *  Create an expression corresponding to the type of the path.
     *  @return expression corresponding to the type of the path
     */
    Expression<Class<? extends X>> type();


    //String-based:
	
    /**
     *  Create a path corresponding to the referenced attribute.
     * 
     *  <p> Note: Applications using the string-based API may need to 
     *  specify the type resulting from the <code>get</code> operation in order
     *  to avoid the use of <code>Path</code> variables.
     *
     *  <pre>
     *     For example:
     *
     *     CriteriaQuery&#060;Person&#062; q = cb.createQuery(Person.class);
     *     Root&#060;Person&#062; p = q.from(Person.class);
     *     q.select(p)
     *      .where(cb.isMember("joe",
     *                         p.&#060;Set&#060;String&#062;&#062;get("nicknames")));
     *
     *     rather than:
     * 
     *     CriteriaQuery&#060;Person&#062; q = cb.createQuery(Person.class);
     *     Root&#060;Person&#062; p = q.from(Person.class);
     *     Path&#060;Set&#060;String&#062;&#062; nicknames = p.get("nicknames");
     *     q.select(p)
     *      .where(cb.isMember("joe", nicknames));
     *  </pre>
     *
     *  @param attributeName  name of the attribute
     *  @return path corresponding to the referenced attribute
     *  @throws IllegalStateException if invoked on a path that
     *          corresponds to a basic type
     *  @throws IllegalArgumentException if attribute of the given
     *           name does not otherwise exist
     */
    <Y> Path<Y> get(String attributeName);
}
