/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.pdb;

import java.io.Serializable;

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
public final class DatabaseField extends AbstractElement<DatabaseField>
		implements SchemaElement<DatabaseField>, Comparable<DatabaseField> {

	private int position;
	private String typeName;
	private boolean notNull;
	private String fullName;
	private boolean primaryKey;
	private boolean isTrnasient;
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

	public boolean hasLinkedTypeName() {
		return linkedTypeName != null && !linkedTypeName.isEmpty();
	}

	/**
	 * Create a field in byte code instruction
	 * 
	 * @param cv
	 *            class writer to field declaration
	 * @since 1.0
	 */
	public void createField(ClassVisitor cv) {
		cv.visitField(Opcodes.ACC_PRIVATE, getName(), Type.getDescriptor(getType()), null, null).visitEnd();
	}

	/**
	 * Create a getter method associated to this field in byte code instruction
	 * 
	 * @param cv
	 *            class writer to method declaration
	 * @param className
	 *            name of the owner class
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
		mv.visitMaxs(0, 0);
	}

	/**
	 * Create a setter method associated to this field in byte code instruction
	 * 
	 * @param cv
	 *            class writer to method declaration
	 * @param className
	 *            name of the owner class
	 * @since 1.0
	 */
	public void createGetter(ClassVisitor cv, String className, String returnType, Class<?> c) {
		String methodName = "get" + getName().substring(0, 1).toUpperCase() + getName().substring(1);
		MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, methodName, "()" + returnType, null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, className, getName(), returnType);
		mv.visitInsn(Type.getType(c).getOpcode(Opcodes.IRETURN));
		mv.visitMaxs(0, 0);
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
	 * Return the {@link DatabaseClass} relationship that connect the owner
	 * class with the field type.
	 * 
	 * @return relationship that connect the owner class with the field type.
	 * @since 1.0
	 */
	public DatabaseClass getLinkedClass() {
		return linkedClass;
	}

	/**
	 * Put the {@link DatabaseClass} relationship that connect the owner class
	 * with the field type.
	 * 
	 * @param clazz
	 *            {@link DatabaseClass} relationship that connect the owner
	 *            class with the field type.
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

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isTrnasient() {
		return isTrnasient;
	}

	public DatabaseField setTrnasient(boolean isTrnasient) {
		this.isTrnasient = isTrnasient;
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

}
