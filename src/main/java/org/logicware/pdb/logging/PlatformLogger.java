/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.logging;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PlatformLogger {

	private static final String LOGGER_ROOT = "META-INF/";
	private static final String LOGGER_BASE_NAME = "logger.properties";
	private static final String LOGGER_REF = LOGGER_ROOT + LOGGER_BASE_NAME;

	private static final String MESSAGE = "Logger File Handler can't be created";
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
			File xyz = new File(Thread.currentThread().getContextClassLoader().getResource(LOGGER_REF).toURI());
			rootlogger.log(Level.FINEST, "Logger Properties Configuration File: {0}", xyz);
			if (xyz.getCanonicalPath().contains("\\target\\classes\\")// Window
					|| xyz.getCanonicalPath().contains("/target/classes/")) { // Linux
				rootlogger.log(Level.FINEST, "Development Mode");
				rootlogger.log(Level.FINEST,
						"" + xyz.getParentFile().getParentFile().getParentFile().getParentFile() + "");
				String currentPath = xyz.getParentFile().getParentFile().getParentFile().getParent();
				file = new FileHandler(currentPath + "/log/" + date + ".log", true);
			} else {
				rootlogger.log(Level.FINEST, "Prodcution Mode");
				rootlogger.log(Level.FINEST,
						"" + xyz.getParentFile().getParentFile().getParentFile().getParentFile() + "");
				String currentPath = xyz.getParentFile().getParent();
				file = new FileHandler(currentPath + "/log/" + date + ".log", true);
			}
		} catch (SecurityException e) {
			rootlogger.log(Level.SEVERE, MESSAGE, e);
		} catch (IOException e) {
			rootlogger.log(Level.SEVERE, MESSAGE, e);
		} catch (URISyntaxException e) {
			rootlogger.log(Level.SEVERE, MESSAGE, e);
		}
		assert file != null;
		file.setFormatter(formatter);
		LOGGER.addHandler(file);
		return new PlatformLogger();
	}

	public final void log(Object sender, Level level, Object message) {
		LOGGER.log(level, sender + "\n" + message, (Throwable) null);
	}

	public final void log(Object sender, Level level, Object message, Throwable throwable) {
		LOGGER.log(level, sender + "\n" + message, throwable);
	}

	public final void trace(Object sender, Object message) {
		log(sender, Level.FINEST, message);
	}

	public final void trace(Object sender, Object message, Throwable throwable) {
		log(sender, Level.FINEST, message, throwable);
	}

	public final void debug(Object sender, Object message) {
		log(sender, Level.FINE, message);
	}

	public final void debug(Object sender, Object message, Throwable throwable) {
		log(sender, Level.FINE, message, throwable);
	}

	public final void info(Object sender, Object message) {
		log(sender, Level.INFO, message);
	}

	public final void info(Object sender, Object message, Throwable throwable) {
		log(sender, Level.INFO, message, throwable);
	}

	public final void warn(Object sender, Object message) {
		log(sender, Level.WARNING, message);
	}

	public final void warn(Object sender, Object message, Throwable throwable) {
		log(sender, Level.WARNING, message, throwable);
	}

	public final void error(Object sender, Object message) {
		log(sender, Level.SEVERE, message);
	}

	public final void error(Object sender, Object message, Throwable throwable) {
		log(sender, Level.SEVERE, message, throwable);
	}

	private PlatformLogger() {
	}

}