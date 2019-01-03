/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 WorkLogic Project
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
/*******************************************************************************
 * Copyright (c) 2008 - 2013 Oracle Corporation. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Linda DeMichiel - Java Persistence 2.1
 *     Linda DeMichiel - Java Persistence 2.0
 *
 ******************************************************************************/ 
package javax.persistence;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.persistence.ConstraintMode.PROVIDER_DEFAULT;

/**
 * Specifies a primary key column that is used as a foreign key to
 * join to another table.
 *
 * <p> It is used to join the primary table of an entity subclass 
 * in the {@link InheritanceType#JOINED JOINED} mapping strategy 
 * to the primary table of its superclass; it is used within a 
 * {@link SecondaryTable} annotation to join a secondary table 
 * to a primary table; and it may be used in a {@link OneToOne} 
 * mapping in which the primary key of the referencing entity 
 * is used as a foreign key to the referenced entity. 
 *
 * <p> If no <code>PrimaryKeyJoinColumn</code> annotation is 
 * specified for a subclass in the <code>JOINED</code>
 * mapping strategy, the foreign key columns are assumed 
 * to have the same names as the primary key columns of the 
 * primary table of the superclass.
 *
 * <pre>
 *
 *    Example: Customer and ValuedCustomer subclass
 *
 *    &#064;Entity
 *    &#064;Table(name="CUST")
 *    &#064;Inheritance(strategy=JOINED)
 *    &#064;DiscriminatorValue("CUST")
 *    public class Customer { ... }
 *    
 *    &#064;Entity
 *    &#064;Table(name="VCUST")
 *    &#064;DiscriminatorValue("VCUST")
 *    &#064;PrimaryKeyJoinColumn(name="CUST_ID")
 *    public class ValuedCustomer extends Customer { ... }
 * </pre>
 *
 * @see SecondaryTable
 * @see Inheritance
 * @see OneToOne
 * @see ForeignKey
 *
 * @since Java Persistence 1.0
 */
@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)

public @interface PrimaryKeyJoinColumn {

    /** 
     * (Optional) The name of the primary key column of the current table.
     * <p> Defaults to the same name as the primary key column 
     * of the primary table of the superclass (<code>JOINED</code> mapping strategy); the same 
     * name as the primary key column of the primary table 
     * (<code>SecondaryTable</code> mapping); or the same name as the 
     * primary key column for the table for the referencing entity 
     * (<code>OneToOne</code> mapping).
     */
    String name() default "";

    /** 
     * (Optional) The name of the primary key column of the table
     * being joined to.  <p> Defaults to the same name as the primary
     * key column of the primary table of the superclass
     * (<code>JOINED</code> mapping strategy); the same name as the
     * primary key column of the primary table
     * (<code>SecondaryTable</code> mapping); or the same name as the
     * primary key column for the table for the referencing entity
     * (<code>OneToOne</code> mapping).
     */
    String referencedColumnName() default "";

    /**
     * (Optional) The SQL fragment that is used when generating the 
     * DDL for the column. This should not be specified for a 
     * <code>OneToOne</code> primary key association.
     * <p> Defaults to the generated SQL to create a column of the 
     * inferred type.
     */
    String columnDefinition() default "";

    /**
     *  (Optional) Used to specify or control the generation of a
     *  foreign key constraint for the primary key join column 
     *  when table generation is in effect.  If
     *  this element is not specified, the persistence provider's
     *  default foreign key strategy will apply.
     *
     *  @since Java Persistence 2.1
     */
    ForeignKey foreignKey() default @ForeignKey(PROVIDER_DEFAULT);
}