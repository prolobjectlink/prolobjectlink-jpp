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
package org.worklogic.domain.geometry;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity(name = "polygon")
@SqlResultSetMapping(name = "PolygonResults", entities = { @EntityResult(entityClass = Polygon.class, fields = {
		@FieldResult(name = "id", column = "Id"), @FieldResult(name = "segment0", column = "Segment0"),
		@FieldResult(name = "segment1", column = "Segment1"),
		@FieldResult(name = "segment2", column = "Segment2") }) }, columns = { @ColumnResult(name = "id") })
public class Polygon {

	@Id
	protected String id;
	protected Segment segment0;
	protected Segment segment1;
	protected Segment segment2;

	public Polygon() {
	}

	public Polygon(String id) {
		this.id = id;
	}

	public Polygon(Segment segment0, Segment segment1, Segment segment2) {
		this.segment0 = segment0;
		this.segment1 = segment1;
		this.segment2 = segment2;
	}

	public Polygon(String id, Segment segment0, Segment segment1, Segment segment2) {
		this.id = id;
		this.segment0 = segment0;
		this.segment1 = segment1;
		this.segment2 = segment2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Segment getSegment0() {
		return segment0;
	}

	public void setSegment0(Segment segment0) {
		this.segment0 = segment0;
	}

	public Segment getSegment1() {
		return segment1;
	}

	public void setSegment1(Segment segment1) {
		this.segment1 = segment1;
	}

	public Segment getSegment2() {
		return segment2;
	}

	public void setSegment2(Segment segment2) {
		this.segment2 = segment2;
	}

	@Override
	public String toString() {
		return "Polygon [id=" + id + ", segment0=" + segment0 + ", segment1=" + segment1 + ", segment2=" + segment2
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((segment0 == null) ? 0 : segment0.hashCode());
		result = prime * result + ((segment1 == null) ? 0 : segment1.hashCode());
		result = prime * result + ((segment2 == null) ? 0 : segment2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Polygon other = (Polygon) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (segment0 == null) {
			if (other.segment0 != null)
				return false;
		} else if (!segment0.equals(other.segment0))
			return false;
		if (segment1 == null) {
			if (other.segment1 != null)
				return false;
		} else if (!segment1.equals(other.segment1))
			return false;
		if (segment2 == null) {
			if (other.segment2 != null)
				return false;
		} else if (!segment2.equals(other.segment2))
			return false;
		return true;
	}

}
