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

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.logicware.db.DatabaseServer;
import org.logicware.logging.LoggerUtils;

public abstract class AbstractWebControl extends AbstractWebPlatform implements WebServerControl {

	private final WebServer webServer;
	private final DatabaseServer databaseServer;

	public AbstractWebControl(WebServer webServer, DatabaseServer databaseServer) {
		this.webServer = webServer;
		this.databaseServer = databaseServer;
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
//		final Map<String, String> serverArgs = DevelopmentServerLauncher.parseArguments(args);
		final Map<String, String> serverArgs = new HashMap<String, String>();

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
