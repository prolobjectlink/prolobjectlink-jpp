/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.logicware.domain.geometry;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@Entity(name = "tetragon")
@SqlResultSetMapping(name = "TetragonResults", entities = { @EntityResult(entityClass = Tetragon.class, fields = {
		@FieldResult(name = "id", column = "Id"), @FieldResult(name = "segment0", column = "Segment0"),
		@FieldResult(name = "segment1", column = "Segment1"), @FieldResult(name = "segment2", column = "Segment2"),
		@FieldResult(name = "segment3", column = "Segment3") }) }, columns = { @ColumnResult(name = "id") })
public class Tetragon extends Polygon {

	private Segment segment3;

	public Tetragon() {
	}

	public Tetragon(String id, Segment segment0, Segment segment1, Segment segment2, Segment segment3) {
		super(id, segment0, segment1, segment2);
		this.segment3 = segment3;
	}

	public Segment getSegment3() {
		return segment3;
	}

	public void setSegment3(Segment segment3) {
		this.segment3 = segment3;
	}

	@Override
	public String toString() {
		return "Tetragon [id=" + id + ", segment0=" + segment0 + ", segment1=" + segment1 + ", segment2=" + segment2
				+ ", segment3=" + segment3 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((segment3 == null) ? 0 : segment3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tetragon other = (Tetragon) obj;
		if (segment3 == null) {
			if (other.segment3 != null)
				return false;
		} else if (!segment3.equals(other.segment3))
			return false;
		return true;
	}

}
