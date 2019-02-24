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
package org.prolobjectlink.db.predicate;

import org.prolobjectlink.db.Predicate;

public final class OrPredicate<O> implements Predicate<O> {

	private final Predicate<O> leftPredicate;
	private final Predicate<O> rigthPredicate;

	private static final long serialVersionUID = 6476411412588653705L;

	public OrPredicate(Predicate<O> leftPredicate, Predicate<O> rigthPredicate) {
		this.leftPredicate = leftPredicate;
		this.rigthPredicate = rigthPredicate;
	}

	public boolean evaluate(final O o) {
		return leftPredicate.evaluate(o) || rigthPredicate.evaluate(o);
	}

}
