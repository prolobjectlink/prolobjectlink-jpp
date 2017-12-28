package org.logicware.jpp;

import static org.logicware.jpp.Level.DEBUG;
import static org.logicware.jpp.Level.ERROR;
import static org.logicware.jpp.Level.INFO;
import static org.logicware.jpp.Level.TRACE;
import static org.logicware.jpp.Level.WARN;

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
