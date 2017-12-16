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

import java.util.List;

public abstract class AbstractProcedureQuery<S> extends AbstractQuery<S> implements ProcedureQuery {

    // prolog procedure name
    private final String functor;

    // prolog procedures arguments
    private final String[] arguments;

    protected AbstractProcedureQuery(String functor, String[] arguments) {
	this.functor = functor;
	this.arguments = arguments;
    }

    public boolean hasMoreElements() {
	// TODO Auto-generated method stub
	return false;
    }

    public Object nextElement() {
	// TODO Auto-generated method stub
	return null;
    }

    public abstract ProcedureQuery setMaxSolution(int maxSolution);

    public abstract ProcedureQuery setFirstSolution(int firstSolution);

    public Object getArgumentValue(int position) {
	// TODO Auto-generated method stub
	return null;
    }

    public Object getArgumentValue(String name) {
	// TODO Auto-generated method stub
	return null;
    }

    public ProcedureQuery setArgumentValue(int position, Object value) {
	// TODO Auto-generated method stub
	return null;
    }

    public ProcedureQuery setArgumentValue(String name, Object value) {
	// TODO Auto-generated method stub
	return null;
    }

    public ProcedureQuery execute() {
	// TODO Auto-generated method stub
	return null;
    }

    public List<Object> getSolutions() {
	// TODO Auto-generated method stub
	return null;
    }

    public Object getSolution() throws NonSolutionError {
	// TODO Auto-generated method stub
	return null;
    }

    public void dispose() {
	// TODO Auto-generated method stub

    }

    protected final String getFunctor() {
	return functor;
    }

    protected final String[] getArguments() {
	return arguments;
    }

}
