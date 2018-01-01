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
