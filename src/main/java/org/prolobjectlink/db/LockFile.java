/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

import org.prolobjectlink.db.etc.Settings;

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
