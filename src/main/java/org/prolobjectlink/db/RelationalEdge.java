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
package org.prolobjectlink.db;

import java.util.Map;

import org.prolobjectlink.GraphEdge;
import org.prolobjectlink.prolog.PrologTerm;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface RelationalEdge<R> extends GraphEdge<R> {

	public Map<PrologTerm, PrologTerm> getCache();

	// one to one

	public boolean isLinkLink();

	// one to many

	public boolean isLinkLinkList();

	public boolean isLinkLinkMap();

	public boolean isLinkLinkSet();

	// many to one

	public boolean isLinkListLink();

	public boolean isLinkMapLink();

	public boolean isLinkSetLink();

	// many to many

	public boolean isLinkListLinkList();

	public boolean isLinkMapLinkList();

	public boolean isLinkSetLinkList();

	public boolean isLinkListLinkMap();

	public boolean isLinkMapLinkMap();

	public boolean isLinkSetLinkMap();

	public boolean isLinkListLinkSet();

	public boolean isLinkMapLinkSet();

	public boolean isLinkSetLinkSet();

	// instances checkers

	public boolean isList(Class<?> clazz);

	public boolean isMap(Class<?> clazz);

	public boolean isSet(Class<?> clazz);

	public boolean isCollection(Class<?> clazz);

}
