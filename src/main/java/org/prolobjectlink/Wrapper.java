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
package org.prolobjectlink;

/**
 * Wrapper class that contains methods for unwrap objects obtaining a down
 * casting objects from some given class.
 * 
 * @author Jose Zalacain
 * @see AbstractWrapper
 * @since 1.0
 */
public interface Wrapper {

	/**
	 * Down cast the current wrapper object to specific given class. Call
	 * {@code Wrapper#unwrap(Object, Class)} passing like argument this instance and
	 * the given class. If down casting is not possible raise a
	 * {@link RuntimeException}.
	 * 
	 * @param     <K> type of object to be cast.
	 * @param cls class of K type to obtain a down cast.
	 * @throws RuntimeException if down casting is not possible
	 * @return current instance of type cls.
	 * @since 1.0
	 * @see #unwrap(Object, Class)
	 */
	<K> K unwrap(Class<K> cls);

	/**
	 * Down cast a given wrapper object to specific given class. If down casting is
	 * not possible raise a {@link RuntimeException}.
	 * 
	 * @param     <K> type of object to be cast.
	 * @param o   object to be cast to K type instance
	 * @param cls class of K type to obtain a down cast.
	 * @throws RuntimeException if down casting is not possible
	 * @return current instance of type cls.
	 * @since 1.0
	 * @see #unwrap(Class)
	 */
	<K> K unwrap(Object o, Class<K> cls);

	/**
	 * Check if the current object can be down cast to an object of type cls class.
	 * More formally perform the boolean class method
	 * {@code Class#isInstance(this)}.
	 * 
	 * @param cls class of to obtain a down cast.
	 * @return true if the current object can be down cast to an object of type cls
	 *         class, false if not
	 * @since 1.0
	 * @see #unwrap(Class)
	 * @see #unwrap(Object, Class)
	 * @see #isWrappedFor(Object, Class)
	 */
	boolean isWrappedFor(Class<?> cls);

	/**
	 * Check if the current object can be down cast to an object of type cls class.
	 * More formally perform the boolean class method {@code Class#isInstance(o)}.
	 * 
	 * @param o   object to be check
	 * @param cls class of to obtain a down cast.
	 * @return true if the current object can be down cast to an object of type cls
	 *         class, false if not
	 * @since 1.0
	 * @see #unwrap(Class)
	 * @see #unwrap(Object, Class)
	 * @see #isWrappedFor( Class)
	 */
	boolean isWrappedFor(Object o, Class<?> cls);

}
