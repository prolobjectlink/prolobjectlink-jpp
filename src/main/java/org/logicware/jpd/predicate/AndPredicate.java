/*
 * #%L
 * prolobjectlink
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpd.predicate;

import org.logicware.jpd.Predicate;

public class AndPredicate<O> implements Predicate<O> {

    private final Predicate<O> leftPredicate;
    private final Predicate<O> rigthPredicate;

    public AndPredicate(Predicate<O> leftPredicate, Predicate<O> rigthPredicate) {
	this.leftPredicate = leftPredicate;
	this.rigthPredicate = rigthPredicate;
    }

    public boolean evaluate(final O o) {
	return leftPredicate.evaluate(o) && rigthPredicate.evaluate(o);
    }

}
