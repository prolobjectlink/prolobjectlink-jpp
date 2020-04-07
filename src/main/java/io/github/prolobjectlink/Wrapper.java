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
package io.github.prolobjectlink;

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
