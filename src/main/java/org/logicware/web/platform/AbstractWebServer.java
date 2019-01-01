/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.logicware.web.platform;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public abstract class AbstractWebServer implements WebServer {

	private final int serverPort;

	public AbstractWebServer(int serverPort) {
		this.serverPort = serverPort;
	}

	public final int getPort() {
		return serverPort;
	}

	public final void start() {

		System.out.println("Server is starting");

		// Pass-through of arguments for Jetty
		final Map<String, String> serverArgs = parseArguments();
//        final Map<String, String> serverArgs = parseArguments(args);

		// Start Jetty
		System.out.println("Starting Jetty servlet container.");
		String url;
		try {
			url = runServer(serverArgs, "Development Server Mode");
			// Start Browser
			if (!serverArgs.containsKey("nogui") && url != null) {
				System.out.println("Starting Web Browser.");

				// Open browser into application URL
//				BrowserLauncher.openBrowser(url);
			}
		} catch (Exception e) {
			// NOP exception already on console by jetty
		}
	}

	public final void restart() {
		stop();
		start();
	}

	public final void stop() {
		// TODO Auto-generated method stub
		System.out.println("Server is stopping");
	}

	/**
	 * Run the server with specified arguments.
	 * 
	 * @param serverArgs
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	protected String runServer(Map<String, String> serverArgs, String mode) throws Exception {

		// Assign default values for some arguments
		assignDefault(serverArgs, "webroot", "WebContent");
		assignDefault(serverArgs, "httpPort", "" + serverPort);
		assignDefault(serverArgs, "context", "");

		int port = serverPort;
		try {
			port = Integer.parseInt(serverArgs.get("httpPort"));
		} catch (NumberFormatException e) {
			// keep default value for port
		}

		// Add help for System.out
		System.out.println("-------------------------------------------------\n" + "Starting Vaadin in " + mode + ".\n"
				+ "Running in http://localhost:" + serverPort
				+ "\n-------------------------------------------------\n");

		final Server server = new Server();

		final ServerConnector connector = new ServerConnector(server);
		connector.setPort(port);
		server.setConnectors(new Connector[] { connector });

		final WebAppContext webappcontext = new WebAppContext();
		String path = getClass().getPackage().getName().replace(".", File.separator);
		webappcontext.setDefaultsDescriptor(path + File.separator + "jetty-webdefault.xml");
		webappcontext.setContextPath(serverArgs.get("context"));
		webappcontext.setWar(serverArgs.get("webroot"));
		server.setHandler(webappcontext);

		try {
			server.start();
		} catch (Exception e) {
			server.stop();
			throw e;
		}

		return "http://localhost:" + port + serverArgs.get("context");
	}

	/**
	 * Assign default value for given key.
	 * 
	 * @param map
	 * @param key
	 * @param value
	 */
	private void assignDefault(Map<String, String> map, String key, String value) {
		if (!map.containsKey(key)) {
			map.put(key, value);
		}
	}

	private Map<String, String> parseArguments() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Parse all command line arguments into a map.
	 * 
	 * Arguments format "key=value" are put into map.
	 * 
	 * @param args
	 * @return map of arguments key value pairs.
	 */
	protected Map<String, String> parseArguments(String[] args) {
		final Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < args.length; i++) {
			final int d = args[i].indexOf("=");
			if (d > 0 && d < args[i].length() && args[i].startsWith("--")) {
				final String name = args[i].substring(2, d);
				final String value = args[i].substring(d + 1);
				map.put(name, value);
			}
		}
		return map;
	}

}
