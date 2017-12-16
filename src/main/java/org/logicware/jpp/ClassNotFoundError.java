/*
 * #%L
 * prolobjectlink
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpp;

public final class ClassNotFoundError extends RuntimeError {

    private static final long serialVersionUID = 2392446209035440426L;

    public ClassNotFoundError(String name) {
	super("The class name " + name + " is not present in classpath");
    }

    public ClassNotFoundError(String name, Throwable cause) {
	super("The class name " + name + " is not present in classpath.", cause);
    }

    public ClassNotFoundError(String name, int fields) {
	super("The class name " + name + " with " + fields + " fields is not present in classpath.");
    }

}
