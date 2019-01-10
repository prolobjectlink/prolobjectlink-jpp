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
package org.worklogic.web.platform;

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
import java.util.Map;

import javax.swing.JOptionPane;

import org.worklogic.db.DatabaseServer;
import org.worklogic.logging.LoggerUtils;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractWebControl extends AbstractWebPlatform implements WebServerControl {

	private final WebServer webServer;
	private final DatabaseServer databaseServer;

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
			final byte buffer[] = new byte[2048];
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

		if (!serverArgs.containsKey("nogui") && url != null) {

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
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
