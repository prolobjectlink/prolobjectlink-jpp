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
package org.logicware.jpi;

public abstract class AbstractClause implements PrologClause {

    private boolean dynamic;
    private boolean multifile;
    private boolean discontiguous;

    private final PrologTerm head;
    private final PrologTerm body;

    /**
     * Fact clause
     * 
     * @param head
     * @param dynamic
     * @param multifile
     * @param discontiguous
     * @since 1.0
     */
    protected AbstractClause(PrologTerm head, boolean dynamic, boolean multifile, boolean discontiguous) {
	this(head, null, dynamic, multifile, discontiguous);
    }

    /**
     * Rule Clause
     * 
     * @param head
     * @param body
     * @param dynamic
     * @param multifile
     * @param discontiguous
     * @since 1.0
     */
    protected AbstractClause(PrologTerm head, PrologTerm body, boolean dynamic, boolean multifile,
	    boolean discontiguous) {
	this.head = head;
	this.body = body;
	this.dynamic = dynamic;
	this.multifile = multifile;
	this.discontiguous = discontiguous;
    }

    public final PrologTerm getTerm() {
	// TODO Auto-generated method stub
	return null;
    }

    public final PrologTerm getHead() {
	return head;
    }

    public final PrologTerm getBody() {
	return body;
    }

    public final String getFunctor() {
	return head.getFunctor();
    }

    public final int getArity() {
	return head.getArity();
    }

    public final String getIndicator() {
	return head.getIndicator();
    }

    public final boolean isDirective() {
	return head == null && body != null;
    }

    public final boolean isFact() {
	return head != null && body == null;
    }

    public final boolean isRule() {
	return head != null && body != null;
    }

    public final boolean unify(PrologClause clause) {
	return head.unify(clause.getHead()) && body.unify(clause.getBody());
    }

    public final boolean isDynamic() {
	return dynamic;
    }

    public void markDynamic() {
	// TODO Auto-generated method stub

    }

    public void unmarkDynamic() {
	// TODO Auto-generated method stub

    }

    public final boolean isMultifile() {
	return multifile;
    }

    public void markMultifile() {
	// TODO Auto-generated method stub

    }

    public void unmarkMultifile() {
	// TODO Auto-generated method stub

    }

    public final boolean isDiscontiguous() {
	return discontiguous;
    }

    public void markDiscontiguous() {
	// TODO Auto-generated method stub

    }

    public void unmarkDiscontiguous() {
	// TODO Auto-generated method stub

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((body == null) ? 0 : body.hashCode());
	result = prime * result + (discontiguous ? 1231 : 1237);
	result = prime * result + (dynamic ? 1231 : 1237);
	result = prime * result + ((head == null) ? 0 : head.hashCode());
	result = prime * result + (multifile ? 1231 : 1237);
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
	AbstractClause other = (AbstractClause) obj;
	if (body == null) {
	    if (other.body != null)
		return false;
	} else if (!body.equals(other.body))
	    return false;
	if (discontiguous != other.discontiguous)
	    return false;
	if (dynamic != other.dynamic)
	    return false;
	if (head == null) {
	    if (other.head != null)
		return false;
	} else if (!head.equals(other.head))
	    return false;
	if (multifile != other.multifile)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return isRule() ? head + ":-\n\t" + body + "." : head + ".";
    }

}
