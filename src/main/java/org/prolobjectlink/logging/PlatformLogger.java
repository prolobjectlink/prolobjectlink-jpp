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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.prolobjectlink.AbstractPlatform;
import org.prolobjectlink.Platform;

public final class PlatformLogger extends AbstractPlatform implements Platform {

	private static final String MESSAGE = "Logger File Handler can't be created";
	public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static PlatformLogger getInstance(Level level) {
		LOGGER.setLevel(level);
		Logger rootlogger = LOGGER.getParent();
		SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
		String date = f.format(new Date());
		Formatter formatter = new LoggerFormatter();
		for (Handler h : rootlogger.getHandlers()) {
			h.setFormatter(formatter);
			h.setLevel(level);
		}
		FileHandler file = null;
		try {
			file = new FileHandler("%t/prolobjectlink-" + date + ".log", true);
		} catch (SecurityException e) {
			rootlogger.log(Level.SEVERE, MESSAGE, e);
		} catch (IOException e) {
			rootlogger.log(Level.SEVERE, MESSAGE, e);
		}
		assert file != null;
		file.setFormatter(formatter);
		LOGGER.addHandler(file);
		return new PlatformLogger();
	}

	public void log(Object sender, Level level, Object message) {
		LOGGER.log(level, sender + "\n" + message, (Throwable) null);
	}

	public void log(Object sender, Level level, Object message, Throwable throwable) {
		LOGGER.log(level, sender + "\n" + message, throwable);
	}

	public void trace(Object sender, Object message) {
		log(sender, Level.FINEST, message);
	}

	public void trace(Object sender, Object message, Throwable throwable) {
		log(sender, Level.FINEST, message, throwable);
	}

	public void debug(Object sender, Object message) {
		log(sender, Level.FINE, message);
	}

	public void debug(Object sender, Object message, Throwable throwable) {
		log(sender, Level.FINE, message, throwable);
	}

	public void info(Object sender, Object message) {
		log(sender, Level.INFO, message);
	}

	public void info(Object sender, Object message, Throwable throwable) {
		log(sender, Level.INFO, message, throwable);
	}

	public void warn(Object sender, Object message) {
		log(sender, Level.WARNING, message);
	}

	public void warn(Object sender, Object message, Throwable throwable) {
		log(sender, Level.WARNING, message, throwable);
	}

	public void error(Object sender, Object message) {
		log(sender, Level.SEVERE, message);
	}

	public void error(Object sender, Object message, Throwable throwable) {
		log(sender, Level.SEVERE, message, throwable);
	}

	private PlatformLogger() {
	}

}
