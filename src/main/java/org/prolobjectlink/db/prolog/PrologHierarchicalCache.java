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
package org.prolobjectlink.db.prolog;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.HierarchicalCache;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.cache.AbstractHierarchicalCache;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class PrologHierarchicalCache extends AbstractHierarchicalCache implements HierarchicalCache {

	private final ContainerFactory containerFactory;

	public PrologHierarchicalCache(PrologProvider provider, Settings properties, ContainerFactory containerFactory) {
		super(provider, properties, new PrologObjectConverter(provider));
		this.containerFactory = containerFactory;
	}

	public PrologHierarchicalCache(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory) {
		super(provider, properties, converter);
		this.containerFactory = containerFactory;
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

}
