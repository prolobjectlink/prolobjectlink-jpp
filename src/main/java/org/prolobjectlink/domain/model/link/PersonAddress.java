/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.domain.model.link;

public final class PersonAddress {

	private final int addressId;
	private final int personId;

	public PersonAddress(int addressId, int personId) {
		this.addressId = addressId;
		this.personId = personId;
	}

	public int getPersonId() {
		return personId;
	}

	public int getAddressId() {
		return addressId;
	}

	@Override
	public String toString() {
		return "PersonAddress [personId=" + personId + ", addressId=" + addressId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + addressId;
		result = prime * result + personId;
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
		PersonAddress other = (PersonAddress) obj;
		if (addressId != other.addressId)
			return false;
		if (personId != other.personId)
			return false;
		return true;
	}

}
