package org.logicware.jpp;

public interface Logger {

	public void log(Object sender, Level level, String message, Object... objects);

	public void log(Object sender, Level level, String message, Throwable throwable, Object... objects);

	public void trace(Object sender, String message, Object... objects);

	public void trace(Object sender, String message, Throwable throwable, Object... objects);

	public void debug(Object sender, String message, Object... objects);

	public void debug(Object sender, String message, Throwable throwable, Object... objects);

	public void info(Object sender, String message, Object... objects);

	public void info(Object sender, String message, Throwable throwable, Object... objects);

	public void warn(Object sender, String message, Object... objects);

	public void warn(Object sender, String message, Throwable throwable, Object... objects);

	public void error(Object sender, String message, Object... objects);

	public void error(Object sender, String message, Throwable throwable, Object... objects);

	public boolean isTraceEnabled();

	public void setTraceEnabled();

	public boolean isDebugEnabled();

	public void setDebugEnabled();

	public boolean isInfoEnabled();

	public void setInfoEnabled();

	public boolean isWarnEnabled();

	public void setWarnEnabled();

	public boolean isErrorEnabled();

	public void setErrorEnabled();

	public void flush();

}
