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

import java.io.File;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Document extends PersistentContainer {

    /**
     * Check that this store have less clauses number in prolog engine that
     * given capacity.
     * 
     * @return true if this store has a clause number lesser than given capacity
     *         or false otherwise
     * @since 1.0
     */
    public boolean hasCapacity();

    public int getCapacity();

    /**
     * The length of the host file for this document.
     * 
     * @return length of the file
     * @since 1.0
     */
    public long getLength();

    public int getSize();

    public File getFile();

    public void setDirty(boolean dirty);

    public boolean isDirty();

    public boolean locked();

    public void open();

    public void flush();

}
