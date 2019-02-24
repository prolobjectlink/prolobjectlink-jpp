/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
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
