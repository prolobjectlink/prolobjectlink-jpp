/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.logging;

import java.util.logging.Level;

public class LoggerUtils {

	private static final PlatformLogger LOGGER = PlatformLogger.getInstance(Level.ALL);

	public static final void trace(Object sender, Object message) {
		LOGGER.log(sender, Level.FINEST, message);
	}

	public static final void trace(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.FINEST, message, throwable);
	}

	public static final void debug(Object sender, Object message) {
		LOGGER.log(sender, Level.FINE, message);
	}

	public static final void debug(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.FINE, message, throwable);
	}

	public static final void info(Object sender, Object message) {
		LOGGER.log(sender, Level.INFO, message);
	}

	public static final void info(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.INFO, message, throwable);
	}

	public static final void warn(Object sender, Object message) {
		LOGGER.log(sender, Level.WARNING, message);
	}

	public static final void warn(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.WARNING, message, throwable);
	}

	public static final void error(Object sender, Object message) {
		LOGGER.log(sender, Level.SEVERE, message);
	}

	public static final void error(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.SEVERE, message, throwable);
	}

	private LoggerUtils() {
	}

}
