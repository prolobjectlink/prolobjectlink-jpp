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
package org.worklogic.domain.classes;

public class OuterInnerClass {

	private int outer;

	public OuterInnerClass() {
	}

	public OuterInnerClass(int outer) {
		this.outer = outer;
	}

	@Override
	public String toString() {
		return "OuterClass [outer=" + outer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + outer;
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
		OuterInnerClass other = (OuterInnerClass) obj;
		if (outer != other.outer)
			return false;
		return true;
	}

	public Object newPrivateInnerClass() {
		return new PrivateInnerClass();
	}

	public Object newPrivateInnerClass(int inner) {
		return new PrivateInnerClass(inner);
	}

	public final class PublicInnerClass {

		private int inner;

		public PublicInnerClass() {
		}

		public PublicInnerClass(int inner) {
			this.inner = inner;
		}

		@Override
		public String toString() {
			return "InnerClass [inner=" + inner + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + inner;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof PublicInnerClass) {
				PublicInnerClass innerObj = (PublicInnerClass) obj;
				return inner == innerObj.inner;
			}
			return false;
		}

		private OuterInnerClass getOuterType() {
			return OuterInnerClass.this;
		}

	}

	private final class PrivateInnerClass {

		private int inner;

		private PrivateInnerClass() {
		}

		private PrivateInnerClass(int inner) {
			this.inner = inner;
		}

		@Override
		public String toString() {
			return "PrivateInnerClass [inner=" + inner + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + inner;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof PrivateInnerClass) {
				PrivateInnerClass innerObj = (PrivateInnerClass) obj;
				return inner == innerObj.inner;
			}
			return false;
		}

		private OuterInnerClass getOuterType() {
			return OuterInnerClass.this;
		}

	}

}
