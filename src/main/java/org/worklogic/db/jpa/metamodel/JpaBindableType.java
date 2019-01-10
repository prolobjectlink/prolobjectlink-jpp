/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.worklogic.db.jpa.metamodel;

import javax.persistence.metamodel.Bindable;

import org.worklogic.db.DatabaseClass;
import org.worklogic.db.Schema;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaBindableType<X> extends JpaIdentifiableType<X> implements Bindable<X> {

	public JpaBindableType(Schema schema, DatabaseClass databaseClass) {
		super(schema, databaseClass);
	}

}
