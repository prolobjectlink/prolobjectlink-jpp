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

import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologProviderFactory;

public final class PersistenceProvider {

	private static final int MAJOR = 1;
	private static final int MINOR = 0;
	private static final int MICRO = 0;

	private PersistenceProvider() {
	}

	public static String getVersion() {
		return MAJOR + "." + MINOR + "." + MICRO;
	}

	public static int getMajorVersion() {
		return MAJOR;
	}

	public static int getMinorVersion() {
		return MINOR;
	}

	public static int getMicroVersion() {
		return MICRO;
	}

	// without properties

	public static ContainerFactory create(Class<?> driver, String provider) {
		return create(driver, PrologProviderFactory.createPrologProvider(provider), new Properties());
	}

	/**
	 * Create and {@link ContainerFactory} instance using your class and setting
	 * a given {@link PrologProvider}
	 * 
	 * @param driver
	 *            concrete class for create an instance of
	 *            {@link ContainerFactory}
	 * @param prolog
	 * @return {@link ContainerFactory} instance
	 * @since 1.0
	 */
	public static ContainerFactory create(Class<?> driver, PrologProvider prolog) {
		return createContainerFactory(driver, prolog, new Properties());
	}

	public static ContainerFactory create(String driver, String provider) {
		return create(driver, PrologProviderFactory.createPrologProvider(provider), new Properties());
	}

	public static ContainerFactory create(String driver, PrologProvider prolog) {
		return createContainerFactory(driver, prolog, new Properties());
	}

	// with properties

	public static ContainerFactory create(Class<?> driver, String provider, Properties parameters) {
		return create(driver, PrologProviderFactory.createPrologProvider(provider), parameters);
	}

	public static ContainerFactory create(Class<?> driver, PrologProvider prolog, Properties parameters) {
		return createContainerFactory(driver, prolog, parameters);
	}

	public static ContainerFactory create(String driver, String provider, Properties parameters) {
		return create(driver, PrologProviderFactory.createPrologProvider(provider), parameters);
	}

	public static ContainerFactory create(String driver, PrologProvider prolog, Properties parameters) {
		return createContainerFactory(driver, prolog, parameters);
	}

	private static ContainerFactory createContainerFactory(String driver, PrologProvider provider, Object object) {
		ContainerFactory containerFactory = null;
		try {
			Class<?> clazz = Class.forName(driver);
			containerFactory = (ContainerFactory) clazz.newInstance();
			containerFactory.setProvider(provider);
			if (object instanceof Properties) {
				Properties parameters = (Properties) object;
				containerFactory.setProperties(parameters);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return containerFactory;
	}

	private static ContainerFactory createContainerFactory(Class<?> driver, PrologProvider provider, Object object) {
		ContainerFactory containerFactory = null;
		try {
			containerFactory = (ContainerFactory) driver.newInstance();
			containerFactory.setProvider(provider);
			if (object instanceof Properties) {
				Properties parameters = (Properties) object;
				containerFactory.setProperties(parameters);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return containerFactory;
	}

}
