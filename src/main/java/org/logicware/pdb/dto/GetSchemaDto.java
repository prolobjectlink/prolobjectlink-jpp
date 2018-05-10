/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.pdb.dto;

import static org.logicware.pdb.DataTransferType.SCHEMA;

import org.logicware.pdb.DataTransferObject;
import org.logicware.pdb.Schema;
import org.logicware.pdb.common.AbstractDataTransfer;

public class GetSchemaDto extends AbstractDataTransfer<Schema, Schema> implements DataTransferObject<Schema, Schema> {

	private static final long serialVersionUID = 4132262055443397931L;

	public GetSchemaDto(Schema query) {
		super(SCHEMA, query);
	}

	public GetSchemaDto() {
		this(null);
	}

}
