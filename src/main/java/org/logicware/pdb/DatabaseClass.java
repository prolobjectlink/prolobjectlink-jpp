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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

public final class DatabaseClass extends AbstractElement implements Comparable<DatabaseClass> {

	private boolean isView;
	private String shortName;
	private boolean isAbstract;
	private String javaClassName;
	private transient Class<?> javaClass;
	private transient DatabaseClass superClass;
	private final List<DatabaseClass> superClasses;
	private final Map<String, DatabaseField> fields;
	private transient DatabaseField primaryKeyField;
	private static final long serialVersionUID = -8770366199140961351L;

	private DatabaseField newField(String name, String comment, int position, Class<?> type) {
		return new DatabaseField(name, comment, position, type, schema, this);
	}

	public DatabaseClass(String name, String comment, Schema schema) {
		this(name, comment, null, schema);
	}

	public DatabaseClass(Class<?> javaClass, String comment, Schema schema) {
		this(javaClass.getName(), comment, javaClass, schema);
	}

	public DatabaseClass(String name, String comment, Class<?> javaClass, Schema schema) {
		super(name, comment, schema);
		if (name.contains("." + "")) {
			int index = name.lastIndexOf('.');
			shortName = name.substring(index + 1);
		} else {
			shortName = name;
		}
		this.javaClassName = javaClass != null ? javaClass.getName() : "";
		this.fields = new HashMap<String, DatabaseField>();
		this.superClasses = new ArrayList<DatabaseClass>();
		this.javaClass = javaClass;
		this.schema = schema;
	}

	public String generate() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append("class");
		buffer.append(' ');
		buffer.append(shortName);
		buffer.append(' ');
		if (superClass != null) {
			buffer.append("extends");
			buffer.append(' ');
			buffer.append(superClass);
		}
		Iterator<DatabaseClass> i = superClasses.iterator();
		if (i.hasNext()) {
			buffer.append("implements");
			buffer.append(' ');
		}
		while (i.hasNext()) {
			DatabaseClass clazz = i.next();
			buffer.append(clazz);
			if (i.hasNext()) {
				buffer.append(',');
				buffer.append(' ');
			}
		}
		buffer.append(' ');
		buffer.append('{');
		buffer.append('\n');
		buffer.append('\n');

		// fields
		DatabaseField[] e = new DatabaseField[0];
		DatabaseField[] a = fields.values().toArray(e);
		Arrays.sort(a, new FieldComparator());
		for (DatabaseField field : a) {
			field.generateField(buffer);
		}
		buffer.append('\n');

		// empty constructor
		buffer.append('\t');
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append(shortName);
		buffer.append("()");
		buffer.append('{');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('}');
		buffer.append('\n');
		buffer.append('\n');

		// full constructor
		buffer.append('\t');
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append(shortName);
		buffer.append('(');

		// TODO respect declaration order

		Iterator<DatabaseField> j = fields.values().iterator();
		while (j.hasNext()) {
			DatabaseField field = j.next();
			field.generateParameter(buffer);
			if (j.hasNext()) {
				buffer.append(',');
				buffer.append(' ');
			}
		}
		buffer.append(')');
		buffer.append('{');
		buffer.append('\n');
		for (DatabaseField field : a) {
			field.generateAssigment(buffer);
		}
		buffer.append('\t');
		buffer.append('}');
		buffer.append('\n');
		buffer.append('\n');

		// methods
		for (DatabaseField field : fields.values()) {
			field.generateGetter(buffer);
			field.generateSetter(buffer);
		}
		buffer.append('}');
		buffer.append('\n');
		return "" + buffer + "";
	}

	public byte[] compile() {
		ClassWriter cw = new ClassWriter(Opcodes.ASM6);
		PrintWriter printWriter = new PrintWriter(System.out);
		TraceClassVisitor tcv = new TraceClassVisitor(cw, printWriter);
		CheckClassAdapter cv = new CheckClassAdapter(tcv);

		String internalName = name.replace('.', '/');

		String superclass = superClass != null ? //
				superClass.getName().replace('.', '/') : //
				Type.getInternalName(Object.class);

		String[] interfaces = new String[superClasses.size()];
		for (int i = 0; i < superClasses.size(); i++) {
			interfaces[i] = superClasses.get(i).name.replace('.', '/');
		}

		cv.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, internalName, null, superclass, interfaces);

		// Fields Declaration
		for (DatabaseField field : fields.values()) {
			field.createField(cv);
		}

		MethodVisitor con = cv.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);

		con.visitCode();
		con.visitVarInsn(Opcodes.ALOAD, 0);
		con.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		con.visitInsn(Opcodes.RETURN);
		con.visitMaxs(1, 1);

		// Fields Getters and Setters
		for (DatabaseField field : fields.values()) {
			Class<?> type = field.getType();
			String typeDescriptor = Type.getDescriptor(type);
			field.createGetter(cv, internalName, typeDescriptor, type);
			field.createSetter(cv, internalName, typeDescriptor, type);
		}

		cv.visitEnd();

		return cw.toByteArray();
	}

	public int compareTo(DatabaseClass o) {
		DatabaseClass thisClass = this;
		if (thisClass.countFields() > o.countFields()) {
			return (Math.abs(thisClass.countFields() - o.countFields()));
		} else if (thisClass.countFields() < o.countFields()) {
			return -1 * (Math.abs(thisClass.countFields() - o.countFields()));
		}
		return name.compareTo(o.getName());
	}

	public DatabaseClass setSchema(Schema schema) {
		this.schema = schema;
		return this;
	}

	public Object newInstance() {
		return ReflectionUtils.newInstance(javaClass);
	}

	public Class<?> getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(Class<?> javaClass) {
		this.javaClassName = javaClass != null ? javaClass.getName() : "";
		this.javaClass = javaClass;
	}

	public String getJavaClassName() {
		return javaClassName;
	}

	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	public boolean isView() {
		return isView;
	}

	public DatabaseClass setView(boolean isView) {
		this.isView = isView;
		return this;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public DatabaseClass setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		return this;
	}

	public DatabaseClass getSuperClass() {
		return superClass;
	}

	public DatabaseClass setSuperClass(DatabaseClass sc) {
		if (sc.equals(this)) {
			// exception here
		}
		this.superClass = sc;
		return this;
	}

	public boolean hasSuperClasses() {
		return superClass != null || !superClasses.isEmpty();
	}

	public Collection<String> getSuperClassesNames() {
		List<String> supers = new ArrayList<String>();
		supers.add(superClass.getName());
		for (DatabaseClass databaseClass : superClasses) {
			supers.add(databaseClass.getName());
		}
		return supers;
	}

	public Collection<DatabaseClass> getSuperClasses() {
		List<DatabaseClass> cs = new ArrayList<DatabaseClass>();
		cs.add(superClass);
		cs.addAll(superClasses);
		return cs;
	}

	public DatabaseClass addSuperClass(DatabaseClass superClass) {
		superClasses.add(superClass);
		return this;
	}

	public DatabaseClass removeSuperClass(DatabaseClass superClass) {
		if (this.superClass.equals(superClass)) {
			this.superClass = null;
		}
		superClasses.remove(superClass);
		return this;
	}

	public boolean isSuperClassOf(DatabaseClass clazz) {
		Collection<DatabaseClass> supers = clazz.getSuperClasses();
		for (DatabaseClass databaseClass : supers) {
			if (this.equals(databaseClass)) {
				return true;
			}
		}
		return false;
	}

	public boolean isSuperClassOf(String name) {
		DatabaseClass c = schema.getClass(name);
		return c != null && c.isSubClassOf(this);
	}

	public String getShortName() {
		return shortName;
	}

	public DatabaseField getPrimaryKeyField() {
		return primaryKeyField;
	}

	public void setPrimaryKeyField(DatabaseField primaryKeyField) {
		this.primaryKeyField = primaryKeyField;
	}

	public DatabaseClass setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}

	public Collection<DatabaseField> getFields() {
		return fields.values();
	}

	public Map<String, DatabaseField> getFieldsMap() {
		return fields;
	}

	public DatabaseField getField(String name) {
		return fields.get(name);
	}

	public boolean hasField(String name) {
		return getField(name) != null;
	}

	/**
	 * Add a field with your respective type. The created field is not marked
	 * like primary key.
	 * 
	 * @param name
	 *            field name
	 * @param type
	 *            type of the field
	 * @return the field created and added to this class
	 * @since 1.0
	 */
	public DatabaseField addField(String name, String comment, int position, Class<?> type) {
		return addField(name, comment, position, type, false);
	}

	/**
	 * Add a field with your respective type and marked like class's primary
	 * key.
	 * 
	 * @param name
	 *            field name
	 * @param type
	 *            type of the field
	 * @param primaryKey
	 *            true if this field is marked like primary key
	 * @return the field created and added to this class
	 * @since 1.0
	 */
	public DatabaseField addField(String name, String comment, int position, Class<?> type, boolean primaryKey) {
		DatabaseField f = newField(name, comment, position, type);
		f.setPrimaryKey(primaryKey);
		fields.put(name, f);
		return f;
	}

	/**
	 * Add a field of type Collection/Map of given class type
	 * 
	 * @param name
	 *            field name
	 * @param type
	 *            type of the field
	 * @param linkedClass
	 *            Generic class for collection/Map
	 * @return the field created and added to this class
	 * @since 1.0
	 */
	@Deprecated
	public DatabaseField addField(String name, String comment, int position, Class<?> type, DatabaseClass linkedClass) {
		DatabaseField f = newField(name, comment, position, type);
		f.setLinkedClass(linkedClass);
		fields.put(name, f);
		return f;
	}

	/**
	 * Add a field of type Collection/Map of given class type
	 * 
	 * @param name
	 *            field name
	 * @param type
	 *            type of the field
	 * @param linkedType
	 *            Generic class for collection/Map
	 * @return the field created and added to this class
	 * @since 1.0
	 */
	public DatabaseField addField(String name, String comment, int position, Class<?> type, Class<?> linkedType) {
		DatabaseField f = newField(name, comment, position, type);
		f.setLinkedType(linkedType);
		fields.put(name, f);
		return f;
	}

	public DatabaseClass removeField(String name) {
		fields.remove(name);
		return this;
	}

	public int countFields() {
		return fields.size();
	}

	public boolean isSubClassOf(String name) {
		if (superClass != null && superClass.getName().equals(name)) {
			return true;
		}
		for (DatabaseClass c : superClasses) {
			if (c.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean isSubClassOf(DatabaseClass clazz) {
		return isSubClassOf(clazz.getName());
	}

	public Collection<DatabaseClass> getSubclasses() {
		Collection<DatabaseClass> sub = new ArrayList<DatabaseClass>();
		Collection<DatabaseClass> all = schema.getClasses();
		for (DatabaseClass databaseClass : all) {
			DatabaseClass sc = databaseClass.getSuperClass();
			if (sc != null && sc.equals(this)) {
				sub.add(databaseClass);
			} else {
				Collection<DatabaseClass> s = databaseClass.getSuperClasses();
				for (DatabaseClass databaseClass2 : s) {
					DatabaseClass sc2 = databaseClass.getSuperClass();
					if (sc2 != null && sc2.getSuperClass().equals(this)) {
						sub.add(databaseClass2);
					}
				}
			}
		}
		return sub;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DatabaseClass other = (DatabaseClass) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private class FieldComparator implements Comparator<DatabaseField> {

		public int compare(DatabaseField o1, DatabaseField o2) {
			return o1.compareTo(o2);
		}
	}

	public DatabaseClass setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public SchemaElementType geElementType() {
		return SchemaElementType.CLASS;
	}

}
