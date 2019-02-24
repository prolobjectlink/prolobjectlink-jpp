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

import org.prolobjectlink.db.DatabaseSchema;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.HierarchicalDatabase;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.engine.AbstractHierarchicalDatabase;
import org.prolobjectlink.db.etc.Settings;

/**
 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
 *             RemoteHierarchical} instead
 * @author Jose Zalacain
 *
 */
@Deprecated
public class PrologHierarchicalDatabase extends AbstractHierarchicalDatabase implements HierarchicalDatabase {

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public PrologHierarchicalDatabase(Settings properties, StorageMode storageMode, String name, DatabaseUser user) {
		super(name, new DatabaseSchema(LOCATION + SEPARATOR + name, properties.getProvider(), properties, user), user,
				new PrologStorageManager(properties.getProvider(), properties,
						new PrologObjectConverter(properties.getProvider()),
						LOCATION + SEPARATOR + name + SEPARATOR + "database", properties, storageMode));
	}

}
