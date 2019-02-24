/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.web.platform;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.prolobjectlink.db.DatabaseServer;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.prolog.ArrayIterator;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractWebControl extends AbstractWebPlatform implements WebServerControl {

	private final WebServer webServer;
	private final DatabaseServer databaseServer;

	// standard output stream
	// private final PrintWriter stdout = System.console().writer()
	private static final PrintStream stdout = System.out;

	public AbstractWebControl(WebServer webServer, DatabaseServer databaseServer) {
		this.webServer = webServer;
		this.databaseServer = databaseServer;
	}

	protected boolean deployWarfile(Map<String, String> args) throws IOException {
		final InputStream warfile = getClass().getResourceAsStream(embeddedWarfileName);
		if (warfile != null) {
			final File tempWarfile = File.createTempFile("embedded", ".war").getAbsoluteFile();
			tempWarfile.getParentFile().mkdirs();
			tempWarfile.deleteOnExit();

			final String webroot = "ProlobjectlinkExplorerWar";
			final File tempWebroot = new File(tempWarfile.getParentFile(), webroot);
			tempWebroot.mkdirs();

			final OutputStream out = new FileOutputStream(tempWarfile, true);
			int read = 0;
			final byte[] buffer = new byte[2048];
			while ((read = warfile.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			out.close();
			warfile.close();

			args.put("warfile", tempWarfile.getAbsolutePath());
			args.put("webroot", tempWebroot.getAbsolutePath());
			args.remove("webappsDir");
			args.remove("hostsDir");
			return true;
		}
		return false;
	}

	public final DatabaseServer getDatabaseServer() {
		return databaseServer;
	}

	public final WebServer getWebServer() {
		return webServer;
	}

	public final Map<String, String> getArguments(String[] args) {
		final Map<String, String> map = new HashMap<String, String>();
		if (args.length > 0) {
			Iterator<String> i = new ArrayIterator<String>(args);
			String name = i.next();
			if (i.hasNext()) {
				String value = i.next();
				map.put(name, value);
			} else {
				map.put(name, "");
			}
		}
		return map;
	}

	public final void printUsage() {
		stdout.println("Usage: prolog option [file] to consult a file");
		stdout.println("options:");
		stdout.println("	-r	consult/run a prolog file");
		stdout.println("	-v	print the prolog engine version");
		stdout.println("	-n	print the prolog engine name");
		stdout.println("	-l	print the prolog engine license");
		stdout.println("	-i	print the prolog engine information");
		stdout.println("	-a	print the prolog engine about");
		stdout.println("	-e	print the prolog engine enviroment paths");
		stdout.println("	-x	start the prolog engine execution");
		stdout.println("	-w	print the current work directory ");
		stdout.println("	-f	consult a prolog file and save formatted code");
	}

	public final void start() {
		webServer.start();
	}

	public final void restart() {
		webServer.restart();
	}

	public final void stop() {
		webServer.stop();
	}

	public final void run(String[] args) {

		final String url = "http://mssql.delnet.cu:8080/";
		final Map<String, String> serverArgs = getArguments(args);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				databaseServer.shutdown();
				webServer.stop();
			}
		});

		if (!serverArgs.containsKey("nogui")) {

			if (!SystemTray.isSupported()) {
				LoggerUtils.info(getClass(), "SystemTray is not supported");
				return;
			}

			SystemTray tray = SystemTray.getSystemTray();
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage("trayIcon.png");

			PopupMenu menu = new PopupMenu();

			MenuItem openItem = new MenuItem("Explorer");
			openItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openBrowser(url);
				}
			});
			menu.add(openItem);

			MenuItem startItem = new MenuItem("Start");
			startItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					webServer.start();
				}
			});
			menu.add(startItem);

			MenuItem stopItem = new MenuItem("Stop");
			startItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					webServer.stop();
				}
			});
			menu.add(stopItem);

			MenuItem configItem = new MenuItem("Config.");
			startItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Configuration");
				}
			});
			menu.add(configItem);

			MenuItem helpItem = new MenuItem("Help");
			helpItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			menu.add(helpItem);

			MenuItem aboutItem = new MenuItem("About");
			aboutItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			menu.add(aboutItem);

			MenuItem closeItem = new MenuItem("Close");
			closeItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			menu.add(closeItem);

			TrayIcon icon = new TrayIcon(image, "Prolobjectlink Server", menu);
			icon.setImageAutoSize(true);
			try {
				tray.add(icon);
			} catch (AWTException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			}

		}
	}

}
