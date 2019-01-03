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

import java.util.Collection;

/**
 * Type for query expressions.
 *
 * @param <T> the type of the expression
 *
 * @since Java Persistence 2.0
 */
public interface Expression<T> extends Selection<T> {

    /**
     *  Create a predicate to test whether the expression is null.
     *  @return predicate testing whether the expression is null
     */
    Predicate isNull();

    /**
     *  Create a predicate to test whether the expression is 
     *  not null.
     *  @return predicate testing whether the expression is not null
     */
    Predicate isNotNull();

    /**
     * Create a predicate to test whether the expression is a member
     * of the argument list.
     * @param values  values to be tested against
     * @return predicate testing for membership
     */
    Predicate in(Object... values);

    /**
     * Create a predicate to test whether the expression is a member
     * of the argument list.
     * @param values  expressions to be tested against
     * @return predicate testing for membership
     */
    Predicate in(Expression<?>... values);

    /**
     * Create a predicate to test whether the expression is a member
     * of the collection.
     * @param values  collection of values to be tested against
     * @return predicate testing for membership
     */
    Predicate in(Collection<?> values);

    /**
     * Create a predicate to test whether the expression is a member
     * of the collection.
     * @param values expression corresponding to collection to be
     *        tested against
     * @return predicate testing for membership
     */
    Predicate in(Expression<Collection<?>> values);

    /**
     * Perform a typecast upon the expression, returning a new
     * expression object.
     * This method does not cause type conversion:
     * the runtime type is not changed.
     * Warning: may result in a runtime failure.
     * @param type  intended type of the expression
     * @return new expression of the given type
     */
    <X> Expression<X> as(Class<X> type);
}
