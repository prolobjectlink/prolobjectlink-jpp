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
package org.prolobjectlink;

/**
 * {@link GraphEdge} direction respect to some {@link GraphVertex}. Support all
 * possible directions of graph edge. There are three possible direction, IN ,
 * OUT and BOTH.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public enum Direction {

	/**
	 * In direction
	 */
	IN,

	/**
	 * Out direction
	 */
	OUT,

	/*
	 * IN-OUT direction
	 */
	BOTH

}
