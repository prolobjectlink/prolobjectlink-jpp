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
