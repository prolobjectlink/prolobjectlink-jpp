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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.prolobjectlink.Licenses;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractTomcatServer extends AbstractWebServer implements TomcatWebServer {

	private final Tomcat tomcatServer = new Tomcat();

	public AbstractTomcatServer(int serverPort) {
		super(serverPort);
		tomcatServer.setPort(serverPort);
	}

	public final String getLicense() {
		return Licenses.APACHE_V2;
	}

	public final String getVersion() {
		// TODO
		return "Tomcat";
	}

	public final String getName() {
		return TOMCAT;
	}

	public void start() {
		File root = getRootFolder();
		System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
		Tomcat tomcat = new Tomcat();
		Path tempPath;
		try {
			tempPath = Files.createTempDirectory("tomcat-base-dir");
			tomcat.setBaseDir(tempPath.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// The port that we should run on can be set into an environment variable
		// Look for that variable and default to 8080 if it isn't there.
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}

		tomcat.setPort(Integer.valueOf(webPort));
		File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
		if (!webContentFolder.exists()) {
			try {
				webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		StandardContext ctx = null;
		try {
			ctx = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Set execution independent of current thread context classloader
		// (compatibility with exec:java mojo)
		ctx.setParentClassLoader(getClass().getClassLoader());

		System.out.println("configuring app with basedir: " + webContentFolder.getAbsolutePath());

		// Declare an alternative location for your "WEB-INF/classes" dir
		// Servlet 3.0 annotation will work
		File additionWebInfClassesFolder = new File(root.getAbsolutePath(), "target/classes");
		WebResourceRoot resources = new StandardRoot(ctx);

		WebResourceSet resourceSet;
		if (additionWebInfClassesFolder.exists()) {
			resourceSet = new DirResourceSet(resources, "/WEB-INF/classes",
					additionWebInfClassesFolder.getAbsolutePath(), "/");
			System.out.println(
					"loading WEB-INF resources from as '" + additionWebInfClassesFolder.getAbsolutePath() + "'");
		} else {
			resourceSet = new EmptyResourceSet(resources);
		}
		resources.addPreResources(resourceSet);
		ctx.setResources(resources);

		try {
			tomcat.start();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	private File getRootFolder() {
		try {
			File root;
			String runningJarPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
					.replaceAll("\\\\", "/");
			int lastIndexOf = runningJarPath.lastIndexOf("/target/");
			if (lastIndexOf < 0) {
				root = new File("");
			} else {
				root = new File(runningJarPath.substring(0, lastIndexOf));
			}
			System.out.println("application resolved root folder: " + root.getAbsolutePath());
			return root;
		} catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}

}
