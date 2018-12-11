/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

/**
 * The file lock is used to lock a database so that only one process can write
 * to it. It uses a cooperative locking protocol. Usually a .lock file is used.
 */
public class LockFile implements Runnable {

	private static final int SLEEP_GAP = 1;

	// The number of milliseconds to wait between checking
	// the .lock file still exists once a database is locked.
	private static final int LOCK_SLEEP = 40;

	private static final int TIME_GRANULARITY = 80;

	// milliseconds to sleep after file checking.
	private final long sleep;

	// The lock file
	private volatile File file;

	// lock indicator flag
	private volatile boolean locked;

	// The last time the lock file was written.
	private long lastWrite;

	// file lock observer thread
	private Thread daemon;

	// file lock properties container
	private Properties properties;

	/**
	 * Create a new file locking object.
	 * 
	 * @param fileName the file name
	 * @param sleep    the number of milliseconds to sleep
	 */
	public LockFile(String fileName, Settings settings) {
		properties = new Properties();
		file = new File(fileName);
		this.sleep = LOCK_SLEEP;
	}

	/**
	 * Lock the file if possible. A file may only be locked once.
	 */
	public synchronized void lock() {
		if (!locked) {
			try {
				generateUniqueId();
				createParentDirectories();
				if (file != null && !file.createNewFile()) {
					waitUntilOld();
					save();
					sleep(2 * sleep);
					checkLockedBefore();
					boolean deleted = file.delete();
					if (deleted && !file.createNewFile()) {
						throw new Exception("Another process was faster");
					}
				}
				save();
				sleep(SLEEP_GAP);
				checkLockedBefore();
				if (file != null) {
					daemon = new Thread(this, "File Lock Daemon" + file.getPath());
					daemon.setPriority(Thread.MAX_PRIORITY - 1);
					daemon.setDaemon(true);
					daemon.start();
				}
				locked = true;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Unlock the file. The watch daemon thread is stopped. This method does nothing
	 * if the file is already unlocked.
	 */
	public synchronized void unlock() {
		if (locked) {
			locked = false;
			try {
				if (daemon != null) {
					daemon.interrupt();
				}
				if (file != null && load().equals(properties) && file.delete()) {
					System.out.println("FileLock delete lock file");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				file = null;
			}
		}

	}

	public boolean islock() {
		return locked;
	}

	public void run() {
		try {
			while (file != null) {
				if (!file.exists() || file.lastModified() != lastWrite) {
					save();
				}
				Thread.sleep(sleep);
			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the properties file.
	 * 
	 * @return the properties
	 */
	private Properties load() {
		for (int i = 0; i < 5; i++) {
			try {
				if (file != null) {
					properties.load(new FileReader(file.getPath()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

	/**
	 * Save the lock file.
	 * 
	 * @return the saved properties
	 * @throws Exception
	 */
	private Properties save() {
		try {
			if (file != null) {
				OutputStream out = new FileOutputStream(file.getPath(), false);
				properties.store(out, "ProlobjectLink File Lock");
				out.close();
				lastWrite = file.lastModified();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	private void waitUntilOld() {
		int n = 2 * TIME_GRANULARITY / SLEEP_GAP;
		for (int i = 0; i < n; i++) {
			long last = file.lastModified();
			long dist = System.currentTimeMillis() - last;
			if (dist < -TIME_GRANULARITY) {
				// lock file modified in the future -
				// wait for a bit longer than usual
				try {
					sleep(2 * sleep);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			} else if (dist > TIME_GRANULARITY) {
				return;
			}
			try {
				sleep(SLEEP_GAP);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void generateUniqueId() {
		try {
			byte[] bytes = secureRandomBytes(16);
			String random = convertBytesToHex(bytes, bytes.length);
			String uniqueId = Long.toHexString(System.currentTimeMillis()) + random;
			properties.setProperty("id", uniqueId);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private void checkLockedBefore() {
		if (!load().equals(properties)) {
			System.out.println("Locked by another process");
		}
	}

	/**
	 * Get a number of cryptographically secure pseudo random bytes.
	 * 
	 * @param len the number of bytes
	 * @return the random bytes
	 * @throws NoSuchAlgorithmException
	 */
	private byte[] secureRandomBytes(int len) throws NoSuchAlgorithmException {
		if (len <= 0) {
			len = 1;
		}
		byte[] buff = new byte[len];
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		synchronized (sr) {
			sr.nextBytes(buff);
		}
		return buff;
	}

	/**
	 * Convert a byte array to a hex encoded string.
	 * 
	 * @param value the byte array
	 * @param len   the number of bytes to encode
	 * @return the hex encoded string
	 */
	private String convertBytesToHex(byte[] value, int len) {
		char[] hex = "0123456789abcdef".toCharArray();
		char[] buff = new char[len + len];
		for (int i = 0; i < len; i++) {
			int c = value[i] & 0xff;
			buff[i + i] = hex[c >> 4];
			buff[i + i + 1] = hex[c & 0xf];
		}
		return new String(buff);
	}

	private void createParentDirectories() {
		if (file != null) {
			String parent = file.getParent();
			if (parent != null) {
				File parentFile = new File(parent);
				parentFile.mkdirs();
			}
		}
	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
