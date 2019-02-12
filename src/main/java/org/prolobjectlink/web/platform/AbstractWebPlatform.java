/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.web.platform;

import java.io.IOException;

import org.prolobjectlink.AbstractPlatform;
import org.prolobjectlink.Platform;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractWebPlatform extends AbstractPlatform implements Platform {

	protected boolean started;
	protected final Runtime runtime;

	public AbstractWebPlatform() {
		runtime = Runtime.getRuntime();
	}

	public final Process run(String cmd) {
		try {
			return runtime.exec(cmd);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		throw new RuntimeException("Can't run process " + cmd);
	}

}
