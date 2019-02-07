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
package org.prolobjectlink.db;

import java.lang.reflect.Field;
import java.util.Map;

import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologVariable;

/**
 * Attribute renamer to prevent variable clashes in prolog queries. Basically
 * convert a class field name in one qualified prolog variable name. The
 * qualified prolog variable name respect the prolog variable syntax. Have
 * methods for convert from {@link Field} to {@link PrologVariable} and
 * vice-versa. E.g
 * 
 * 
 * from {@code org.logicware.domain.geometry.Point.idp} to
 * {@code ORG_LOGICWARE_DOMAIN_GEOMETRY_POINT_idp_0} from
 * {@code ORG_LOGICWARE_DOMAIN_GEOMETRY_POINT_idp_0 } to
 * {@code org.logicware.domain.geometry.Point.idp}
 * 
 * 
 * @author Jose Zalacain
 * @see #toField(PrologVariable)
 * @see #toVariable(Field)
 * @since 1.0
 */
public interface Renamer {

	public Map<String, Class<?>> getVariableMap();

	/**
	 * Convert from {@link PrologVariable} to {@link Field} e.g from
	 * {@code ORG_LOGICWARE_DOMAIN_GEOMETRY_POINT_idp_0 } to
	 * {@code org.logicware.domain.geometry.Point.idp}
	 * 
	 * 
	 * @param variable prolog variable to be converted
	 * @return equivalent filed to prolog variable.
	 * @since 1.0
	 */
	public Field toField(PrologVariable variable);

	/**
	 * Convert from {@link Field} to {@link PrologVariable} e.g from
	 * {@code org.logicware.domain.geometry.Point.idp} to
	 * {@code ORG_LOGICWARE_DOMAIN_GEOMETRY_POINT_idp_0}
	 * 
	 * @param field to be converted
	 * @return prolog variable renamed that represent this field
	 * @since 1.0
	 */
	public PrologVariable toVariable(Field field);

	public PrologProvider getProvider();

	public Field toField(String name);

}
