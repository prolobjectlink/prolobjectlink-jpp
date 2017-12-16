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
package org.logicware.jpd;

public class Properties {

    // connection

    /**
     * 
     */
    public String url;

    /**
     * 
     */
    public String driver;

    // engine

    /**
     * 
     */
    public String engine;

    /**
     * 
     */
    public Integer arity;

    /**
     * 
     */
    public Integer stack;

    /**
     * 
     * @param url
     * @return
     */
    public Properties url(String url) {
	this.url = url;
	return this;
    }

    /**
     * 
     * @param driver
     * @return
     */
    public Properties driver(String driver) {
	this.driver = driver;
	return this;
    }

    /**
     * 
     * @param engine
     * @return
     */
    public Properties engine(String engine) {
	this.engine = engine;
	return this;
    }

    /**
     * 
     * @param arity
     * @return
     */
    public Properties arity(Integer arity) {
	this.arity = arity;
	return this;
    }

    /**
     * 
     * @param stack
     * @return
     */
    public Properties stack(Integer stack) {
	this.stack = stack;
	return this;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((arity == null) ? 0 : arity.hashCode());
	result = prime * result + ((driver == null) ? 0 : driver.hashCode());
	result = prime * result + ((engine == null) ? 0 : engine.hashCode());
	result = prime * result + ((stack == null) ? 0 : stack.hashCode());
	result = prime * result + ((url == null) ? 0 : url.hashCode());
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
	Properties other = (Properties) obj;
	if (arity == null) {
	    if (other.arity != null)
		return false;
	} else if (!arity.equals(other.arity))
	    return false;
	if (driver == null) {
	    if (other.driver != null)
		return false;
	} else if (!driver.equals(other.driver))
	    return false;
	if (engine == null) {
	    if (other.engine != null)
		return false;
	} else if (!engine.equals(other.engine))
	    return false;
	if (stack == null) {
	    if (other.stack != null)
		return false;
	} else if (!stack.equals(other.stack))
	    return false;
	if (url == null) {
	    if (other.url != null)
		return false;
	} else if (!url.equals(other.url))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return ""

		+ "url=" + url + "\n"

		+ "driver=" + driver + "\n"

		+ "engine=" + engine + "\n"

		+ "arity=" + arity + "\n"

		+ "stack=" + stack;
    }

}
