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

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.Map;

import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;
import org.logicware.pdb.util.JavaReflect;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.SerialVersionUIDAdder;

/**
 * This class make {@link Serializable} an object if the object type is not
 * explicit {@link Serializable} declared. Use for serialize objects to send
 * from client to server.
 * 
 * TODO Serializable fields analysis
 * 
 * @author Jose Zalacain
 * @since 1.0
 * 
 * @deprecated Non use this class for send object to server because the
 *             generated class version cannot be handled by Java
 */
@Deprecated
public class Serializer extends JavaReflect {

	private Serializer() {
	}

	private static final Thread currentThread = Thread.currentThread();
	private static final DynamicClassLoader classLoader = new DynamicClassLoader();
	private static final ClassLoader loader = currentThread.getContextClassLoader();
	private static final Map<Class<?>, Class<?>> cachedClasses = new IdentityHashMap<Class<?>, Class<?>>();

	public static <O> Class<O> makeClass(O object) {

		// get the object default class
		Class<?> clazz = object.getClass();

		// check serialized contention
		Class<?>[] ifaces = clazz.getInterfaces();
		for (int i = 0; i < ifaces.length; i++) {
			if (ifaces[i] == Serializable.class) {
				return (Class<O>) clazz;
			}
		}

		// load modified class if is defined
		Class<?> newClass = cachedClasses.get(clazz);
		if (newClass == null) {

			//
			LoggerUtils.debug(Serializer.class, "LOADING... " + clazz);

			// gets an input stream to read the byte code of the class
			String resource = Type.getInternalName(clazz) + ".class";
			InputStream is = loader.getResourceAsStream(resource);

			// adapts the class on the fly (runtime)
			// add serializable interface implementation
			// add generated serialVersionUID field
			try {
				ClassWriter cw = new ClassWriter(Opcodes.ASM5);
				SerialVersionUIDAdder sv = new SerialVersionUIDAdder(cw);
				SerializableAdder serializableAdder = new SerializableAdder(sv);
				new ClassReader(is).accept(serializableAdder, 0);
				newClass = classLoader.defineClass(clazz.getName(), cw.toByteArray());
				cachedClasses.put(clazz, newClass);
			} catch (Exception e) {
				LoggerUtils.error(Serializer.class, LoggerConstants.CLASS_NOT_FOUND, e);
			}

			//
			LoggerUtils.debug(Serializer.class, "LOADED " + clazz);
		}

		return (Class<O>) newClass;
	}

	/**
	 * Get the serialized version of object class creating a new serialized instance
	 * of the given object and copy all value from the given object to the
	 * serialized version.
	 * 
	 * @param object to be serialized
	 * @return serialized version of the given object
	 * @since 1.0
	 */
	public static <O> Serializable make(O object) {
		Class<?> newClass = makeClass(object);
		if (newClass != null) {
			Field[] fields = object.getClass().getDeclaredFields();
			Object serializable = JavaReflect.newInstance(newClass);
			Field[] serializableFields = newClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Object value = readValue(fields[i], object);
				writeValue(serializableFields[i], serializable, value);
			}
			return (Serializable) serializable;
		}
		return null;
	}

	/**
	 * Get the serialized version of object array creating a new serialized array
	 * instance of the given object array.
	 * 
	 * @param objects to be serialized
	 * @return serialized version of the given objects
	 * @since 1.0
	 */
	public static <O> Serializable[] make(O... objects) {
		Serializable[] x = new Serializable[objects.length];
		for (int i = 0; i < objects.length; i++) {
			x[i] = make(objects[i]);
		}
		return x;
	}

	private static class SerializableAdder extends ClassVisitor {

		private SerializableAdder(ClassVisitor cv) {
			super(Opcodes.ASM5, cv);
		}

		@Override
		public void visit(int version, int access, String name, String signature, String superName,
				String[] interfaces) {
			String[] array = new String[interfaces.length + 1];
			System.arraycopy(interfaces, 0, array, 0, interfaces.length);
			array[interfaces.length] = Type.getInternalName(Serializable.class);
			cv.visit(version, access, name, signature, superName, array);
		}

	}

}
