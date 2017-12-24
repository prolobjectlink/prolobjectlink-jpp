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
package org.logicware.jpd;

import org.logicware.jpd.tools.Backup;
import org.logicware.jpd.tools.Restore;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

public abstract class AbstractPersistentDocument extends AbstractPersistentContainer implements PersistentDocument {

	protected AbstractPersistentDocument(PrologProvider provider, Properties properties,
			ObjectConverter<PrologTerm> converter, String location) {
		super(provider, properties, converter, location);
	}

	public final void backup(String directory, String zipFileName) {
		Backup backup = new Backup(getLocation());
		backup.createBackup(directory, zipFileName);
	}

	public final void restore(String directory, String zipFileName) {
		Restore restore = new Restore();
		restore.restoreBackup(directory, zipFileName);
	}

}
