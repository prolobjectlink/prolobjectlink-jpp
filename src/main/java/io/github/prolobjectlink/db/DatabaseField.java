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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Represent a field for a database class in the database schema
 * 
 * @author Jose Zalacain
 * @see Schema
 * @see DatabaseClass
 * @since 1.0
 * 
 */
public class DatabaseField extends AbstractElement<DatabaseField>
		implements SchemaElement<DatabaseField>, Comparable<DatabaseField> {

	private int position;
	protected String typeName;
	private boolean notNull;
	private String fullName;
	private boolean version;
	private boolean oneToOne;
	private boolean oneToMany;
	private boolean manyToOne;
	private boolean manyToMany;
	private boolean primaryKey;
	private boolean isTransient;
	private String linkedTypeName;
	private Serializable minValue;
	private Serializable maxValue;
	private Serializable defaultValue;
	private DatabaseClass linkedClass;
	private transient Class<?> javaType;
	private transient Class<?> linkedType;
	private static final long serialVersionUID = 3864527141246876427L;

	/**
	 * use for internal reflection only
	 */
	protected DatabaseField() {

	}

	public DatabaseField(String name, String comment, int position, Class<?> type, Schema schema, DatabaseClass owner) {
		super(name, comment, schema);
		this.fullName = owner.getName() + "." + name;
		this.typeName = type.getName();
		this.position = position;
		this.javaType = type;
	}

	public void generateField(StringBuilder buffer) {
		int index = typeName.lastIndexOf('.');
		String n = typeName.substring(index + 1);
		buffer.append('\t');
		buffer.append(Modifier.PRIVATE);
		buffer.append(' ');
		buffer.append(n);
		if (hasLinkedTypeName()) {
			buffer.append('<');
			buffer.append(getLinkedTypeShortName());
			buffer.append('>');
		}
		buffer.append(' ');
		buffer.append(getName());
		buffer.append(';');
		buffer.append('\n');
	}

	public void generateGetter(StringBuilder buffer) {
		String fieldName = getName();
		char n = Character.toUpperCase(fieldName.charAt(0));
		String fname = n + fieldName.substring(1);

		// get
		buffer.append('\t');
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append(getType().getSimpleName());
		if (hasLinkedTypeName()) {
			buffer.append('<');
			buffer.append(getLinkedTypeShortName());
			buffer.append('>');
		}
		buffer.append(' ');
		buffer.append("get");
		buffer.append(fname);
		buffer.append("()");
		buffer.append('{');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("return");
		buffer.append(' ');
		buffer.append(fieldName);
		buffer.append(';');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('}');
		buffer.append('\n');
		buffer.append('\n');
	}

	public void generateSetter(StringBuilder buffer) {
		String fieldName = getName();
		char n = Character.toUpperCase(fieldName.charAt(0));
		String fname = n + fieldName.substring(1);
		buffer.append('\t');
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append("void");
		buffer.append(' ');
		buffer.append("set");
		buffer.append(fname);
		buffer.append('(');
		buffer.append(getType().getSimpleName());
		if (hasLinkedTypeName()) {
			buffer.append('<');
			buffer.append(getLinkedTypeShortName());
			buffer.append('>');
		}
		buffer.append(' ');
		buffer.append(fieldName);
		buffer.append(')');
		buffer.append('{');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("this");
		buffer.append(".");
		buffer.append(fieldName);
		buffer.append(' ');
		buffer.append('=');
		buffer.append(' ');
		buffer.append(fieldName);
		buffer.append(';');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('}');
		buffer.append('\n');
		buffer.append('\n');
	}

	public void generateParameter(StringBuilder buffer) {
		buffer.append(getType().getSimpleName());
		if (hasLinkedTypeName()) {
			buffer.append('<');
			buffer.append(getLinkedTypeShortName());
			buffer.append('>');
		}
		buffer.append(' ');
		buffer.append(getName());
	}

	public void generateAssigment(StringBuilder buffer) {
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("this");
		buffer.append(".");
		buffer.append(getName());
		buffer.append(' ');
		buffer.append('=');
		buffer.append(' ');
		buffer.append(getName());
		buffer.append(';');
		buffer.append('\n');
	}

	public void generateAdderer(StringBuilder buffer) {
		if (isList(getType()) || isSet(getType()) || isCollection(getType())) {
			String fieldName = getName();
			char n = Character.toUpperCase(fieldName.charAt(0));
			String fname = n + fieldName.substring(1);
			buffer.append('\t');
			buffer.append(Modifier.PUBLIC);
			buffer.append(' ');
			buffer.append("void");
			buffer.append(' ');
			buffer.append("add");
			buffer.append(fname);
			buffer.append('(');
			if (hasLinkedTypeName()) {
				buffer.append(getLinkedTypeShortName());
			}
			buffer.append(' ');
			buffer.append(fieldName.charAt(0));
			buffer.append(')');
			buffer.append('{');
			buffer.append('\n');
			buffer.append('\t');
			buffer.append('\t');
			buffer.append("this");
			buffer.append(".");
			buffer.append(fieldName);
			buffer.append('.');
			buffer.append("add(");
			buffer.append(fieldName.charAt(0));
			buffer.append(')');
			buffer.append(';');
			buffer.append('\n');
			buffer.append('\t');
			buffer.append('}');
			buffer.append('\n');
			buffer.append('\n');
		}
	}

	public void generateRemover(StringBuilder buffer) {
		if (isList(getType()) || isSet(getType()) || isCollection(getType())) {
			String fieldName = getName();
			char n = Character.toUpperCase(fieldName.charAt(0));
			String fname = n + fieldName.substring(1);
			buffer.append('\t');
			buffer.append(Modifier.PUBLIC);
			buffer.append(' ');
			buffer.append("void");
			buffer.append(' ');
			buffer.append("remove");
			buffer.append(fname);
			buffer.append('(');
			if (hasLinkedTypeName()) {
				buffer.append(getLinkedTypeShortName());
			}
			buffer.append(' ');
			buffer.append(fieldName.charAt(0));
			buffer.append(')');
			buffer.append('{');
			buffer.append('\n');
			buffer.append('\t');
			buffer.append('\t');
			buffer.append("this");
			buffer.append(".");
			buffer.append(fieldName);
			buffer.append('.');
			buffer.append("remove(");
			buffer.append(fieldName.charAt(0));
			buffer.append(')');
			buffer.append(';');
			buffer.append('\n');
			buffer.append('\t');
			buffer.append('}');
			buffer.append('\n');
			buffer.append('\n');
		}
	}

	
	public boolean hasLinkedTypeName() {
		return linkedTypeName != null && !linkedTypeName.isEmpty();
	}

	/**
	 * Create a field in byte code instruction
	 * 
	 * @param cv class writer to field declaration
	 * @since 1.0
	 */
	public void createField(ClassVisitor cv) {
		cv.visitField(Opcodes.ACC_PRIVATE, getName(), Type.getDescriptor(getType()), null, null).visitEnd();
	}

	/**
	 * Create a getter method associated to this field in byte code instruction
	 * 
	 * @param cv        class writer to method declaration
	 * @param className name of the owner class
	 * @since 1.0
	 */
	public void createSetter(ClassVisitor cv, String className, String type, Class<?> c) {
		String methodName = "set" + getName().substring(0, 1).toUpperCase() + getName().substring(1);
		MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, methodName, "(" + type + ")V", null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitVarInsn(Type.getType(c).getOpcode(Opcodes.ILOAD), Type.getType(c).getSize());
		mv.visitFieldInsn(Opcodes.PUTFIELD, className, getName(), type);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();
	}

	/**
	 * Create a setter method associated to this field in byte code instruction
	 * 
	 * @param cv        class writer to method declaration
	 * @param className name of the owner class
	 * @since 1.0
	 */
	public void createGetter(ClassVisitor cv, String className, String returnType, Class<?> c) {
		String methodName = "get" + getName().substring(0, 1).toUpperCase() + getName().substring(1);
		MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, methodName, "()" + returnType, null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, className, getName(), returnType);
		mv.visitInsn(Type.getType(c).getOpcode(Opcodes.IRETURN));
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}

	public int compareTo(DatabaseField o) {
		if (position < o.position) {
			return -1 * Math.abs(position - o.position);
		} else if (position > o.position) {
			return Math.abs(position - o.position);
		}
		return getName().compareTo(o.getName());
	}

	public int getPosition() {
		return position;
	}

	public String getFullName() {
		return fullName;
	}

	public Class<?> getType() {
		return javaType;
	}

	public DatabaseField setType(Class<?> type) {
		this.typeName = type.getName();
		this.javaType = type;
		return this;
	}

	/**
	 * Return the {@link DatabaseClass} relationship that connect the owner class
	 * with the field type.
	 * 
	 * @return relationship that connect the owner class with the field type.
	 * @since 1.0
	 */
	public DatabaseClass getLinkedClass() {
		return linkedClass;
	}

	/**
	 * Put the {@link DatabaseClass} relationship that connect the owner class with
	 * the field type.
	 * 
	 * @param clazz {@link DatabaseClass} relationship that connect the owner class
	 *              with the field type.
	 * @since 1.0
	 */
	public DatabaseField setLinkedClass(DatabaseClass clazz) {
		this.linkedClass = clazz;
		return this;
	}

	public Class<?> getLinkedType() {
		return linkedType;
	}

	public DatabaseField setLinkedType(Class<?> linkedType) {
		this.linkedTypeName = linkedType.getName();
		this.linkedType = linkedType;
		return this;
	}

	public String getLinkedTypeName() {
		return linkedTypeName;
	}

	public String getLinkedTypeShortName() {
		int i = linkedTypeName.lastIndexOf('.');
		return linkedTypeName.substring(i + 1);
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public DatabaseField setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
		return this;
	}

	public boolean isOneToOne() {
		return oneToOne;
	}

	public void setOneToOne(boolean oneToOne) {
		this.oneToOne = oneToOne;
	}

	public boolean isOneToMany() {
		return oneToMany;
	}

	public void setOneToMany(boolean oneToMany) {
		this.oneToMany = oneToMany;
	}

	public boolean isManyToOne() {
		return manyToOne;
	}

	public void setManyToOne(boolean manyToOne) {
		this.manyToOne = manyToOne;
	}

	public boolean isManyToMany() {
		return manyToMany;
	}

	public void setManyToMany(boolean manyToMany) {
		this.manyToMany = manyToMany;
	}

	public boolean isVersion() {
		return version;
	}

	public DatabaseField setVersion(boolean version) {
		this.version = version;
		return this;
	}

	public boolean isTransient() {
		return isTransient;
	}

	public DatabaseField setTransient(boolean isTrnasient) {
		this.isTransient = isTrnasient;
		return this;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public DatabaseField setNotNull(boolean notNull) {
		this.notNull = notNull;
		return this;
	}

	public Object getMinValue() {
		return minValue;
	}

	public DatabaseField setMinValue(Serializable min) {
		if (getType() == min.getClass()) {
			this.minValue = min;
		}
		return this;
	}

	public Object getMaxValue() {
		return maxValue;
	}

	public DatabaseField setMaxValue(Serializable max) {
		if (getType() == max.getClass()) {
			this.maxValue = max;
		}
		return this;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public DatabaseField setDefaultValue(Serializable value) {
		if (getType() == value.getClass()) {
			this.defaultValue = value;
		}
		return this;
	}

	public DatabaseField setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public DatabaseField setSchema(Schema schema) {
		this.schema = schema;
		return this;
	}

	public SchemaElementType geElementType() {
		return SchemaElementType.FIELD;
	}

	public boolean isBasicType() {
		Package pack = getType().getPackage();
		String name = pack.getName();
		String basic = "java.lang";
		return name.contains(basic);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + position;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseField other = (DatabaseField) obj;
		return position == other.position;
	}

	@Override
	public String toString() {
		return getName();
	}

	public final boolean isList(Class<?> clazz) {
		return clazz.isAssignableFrom(List.class);
	}

	public final boolean isMap(Class<?> clazz) {
		return clazz.isAssignableFrom(Map.class);
	}

	public final boolean isSet(Class<?> clazz) {
		return clazz.isAssignableFrom(Set.class);
	}

	public final boolean isCollection(Class<?> clazz) {
		return clazz.isAssignableFrom(Collection.class);
	}

	public final boolean isList() {
		return isList(getType());
	}

	public final boolean isMap() {
		return isMap(getType());
	}

	public final boolean isSet() {
		return isSet(getType());
	}

	public final boolean isCollection() {
		return isCollection(getType());
	}

}
