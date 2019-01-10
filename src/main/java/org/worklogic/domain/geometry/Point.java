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

@Entity(name = "point")
@SqlResultSetMapping(name = "PointResults", entities = {
		@EntityResult(entityClass = Point.class, fields = { @FieldResult(name = "idp", column = "Idp"),
				@FieldResult(name = "x", column = "X"), @FieldResult(name = "y", column = "Y") }) }, columns = {
						@ColumnResult(name = "idp") })
public class Point {

	@Id
	private String idp;
	private int x;
	private int y;

	public Point() {
	}

	public Point(String idp) {
		this.idp = idp;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(String idp, int x, int y) {
		this.idp = idp;
		this.x = x;
		this.y = y;
	}

	public String getIdp() {
		return idp;
	}

	public void setIdp(String idp) {
		this.idp = idp;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [idp=" + idp + ", x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idp == null) ? 0 : idp.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Point other = (Point) obj;
		if (idp == null) {
			if (other.idp != null)
				return false;
		} else if (!idp.equals(other.idp))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		return Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y);
	}

}
