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

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.logicware.database.DatabaseServer;

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

			// Open control dialog
			/*
			 * Swing components should never be manipulated outside the event dispatch
			 * thread.
			 */
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						showServerControl(url);
					} catch (HeadlessException e) {
						// nop, starting from console
					}
				}
			});
		}
	}

	private void showServerControl(final String applicationUrl) {

		// Main frame
		final String title = "Desktop Server";
		final JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// Create link label and listen mouse click
		final JLabel link = new JLabel(
				"<html>" + "<center>Desktop Server is running at: <br>" + "<a href=\"" + applicationUrl + "\">"
						+ applicationUrl + "</a><br>Close this window to shutdown the server.</center>" + "</html>");
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openBrowser(applicationUrl);
			}
		});

		// Create a panel and add components to it.
		final JPanel contentPane = new JPanel(new FlowLayout());
		frame.setContentPane(contentPane);
		contentPane.add(link);

		// Close confirmation
		final JLabel question = new JLabel("This will stop the server. Are you sure?");
		final JButton okButton = new JButton("OK");
		final JButton cancelButton = new JButton("Cancel");

		// List for close verify buttons
		final ActionListener buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == okButton) {
					System.exit(0);
				} else {
					Rectangle bounds = frame.getBounds();
					frame.setTitle(title);
					contentPane.removeAll();
					contentPane.add(link);
					contentPane.setBounds(bounds);
					frame.setBounds(bounds);
					frame.setVisible(true);
					frame.repaint();
				}
			}
		};
		okButton.addActionListener(buttonListener);
		cancelButton.addActionListener(buttonListener);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				final Rectangle bounds = frame.getBounds();
				frame.setTitle("Confirm close");
				contentPane.removeAll();
				contentPane.add(question);
				contentPane.add(okButton);
				contentPane.add(cancelButton);
				frame.setBounds(bounds);
				frame.setVisible(true);
				frame.repaint();
			}
		});

		// Position the window nicely
		final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		final int w = 270;
		final int h = 95;
		final int margin = 20;
		frame.setBounds(new Rectangle(screenSize.width - w - margin, screenSize.height - h - margin * 2, w, h));
		frame.toFront();
		frame.setVisible(true);
	}

}
