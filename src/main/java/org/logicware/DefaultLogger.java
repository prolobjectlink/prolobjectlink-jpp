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
package org.logicware;

import static org.logicware.Level.DEBUG;
import static org.logicware.Level.ERROR;
import static org.logicware.Level.INFO;
import static org.logicware.Level.TRACE;
import static org.logicware.Level.WARN;

import java.io.PrintStream;

public final class DefaultLogger implements Logger {

	protected Level level = Level.WARN;
	protected final PrintStream printStream;

	public DefaultLogger(PrintStream printStream) {
		this.printStream = printStream;
	}

	public final void log(Object sender, Level level, String message, Throwable throwable, Object... objects) {
		// TODO Auto-generated method stub

	}

	public final void log(Object sender, Level level, String message, Object... objects) {
		log(sender, level, message, null, objects);
	}

	public final void trace(Object sender, String message, Object... objects) {
		if (isTraceEnabled()) {
			log(sender, TRACE, message, objects);
		}
	}

	public final void trace(Object sender, String message, Throwable throwable, Object... objects) {
		if (isTraceEnabled()) {
			log(sender, TRACE, message, throwable, objects);
		}
	}

	public final void debug(Object sender, String message, Object... objects) {
		if (isDebugEnabled()) {
			log(sender, DEBUG, message, objects);
		}
	}

	public final void debug(Object sender, String message, Throwable throwable, Object... objects) {
		if (isDebugEnabled()) {
			log(sender, DEBUG, message, throwable, objects);
		}
	}

	public final void info(Object sender, String message, Object... objects) {
		if (isInfoEnabled()) {
			log(sender, INFO, message, objects);
		}
	}

	public final void info(Object sender, String message, Throwable throwable, Object... objects) {
		if (isInfoEnabled()) {
			log(sender, INFO, message, throwable, objects);
		}
	}

	public final void warn(Object sender, String message, Object... objects) {
		if (isWarnEnabled()) {
			log(sender, WARN, message, objects);
		}
	}

	public final void warn(Object sender, String message, Throwable throwable, Object... objects) {
		if (isWarnEnabled()) {
			log(sender, WARN, message, throwable, objects);
		}
	}

	public final void error(Object sender, String message, Object... objects) {
		if (isErrorEnabled()) {
			log(sender, INFO, message, objects);
		}
	}

	public final void error(Object sender, String message, Throwable throwable, Object... objects) {
		if (isErrorEnabled()) {
			log(sender, INFO, message, throwable, objects);
		}
	}

	public final boolean isTraceEnabled() {
		return level.compareTo(TRACE) <= 0;
	}

	public final void setTraceEnabled() {
		this.level = TRACE;
	}

	public final boolean isDebugEnabled() {
		return level.compareTo(DEBUG) <= 0;
	}

	public final void setDebugEnabled() {
		this.level = DEBUG;
	}

	public final boolean isInfoEnabled() {
		return level.compareTo(INFO) <= 0;
	}

	public final void setInfoEnabled() {
		this.level = INFO;
	}

	public final boolean isWarnEnabled() {
		return level.compareTo(WARN) <= 0;
	}

	public final void setWarnEnabled() {
		this.level = WARN;
	}

	public final boolean isErrorEnabled() {
		return level.compareTo(ERROR) <= 0;
	}

	public final void setErrorEnabled() {
		this.level = ERROR;
	}

	public final void flush() {
		printStream.flush();
	}

}
