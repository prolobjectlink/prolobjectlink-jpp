/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db;

import java.lang.reflect.Field;
import java.util.Map;

import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologVariable;

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
