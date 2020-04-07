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
package io.github.prolobjectlink.db;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.github.prolobjectlink.AbstractWrapper;
import io.github.prolobjectlink.Wrapper;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;

public final class DatabaseRequest extends AbstractWrapper
		implements Wrapper, Serializable, Comparable<DatabaseRequest> {

	private final int port;
	private long timestamp;
	private OperationType type;
	private DatabaseType dbtype;
	private final String dbname;
	private final String server;
	private static final int MAX_ARG_NUM = 2;
	private static final long serialVersionUID = -8793751981186352447L;
	private final ArrayList<Serializable> arguments = new ArrayList<Serializable>(MAX_ARG_NUM);

	public DatabaseRequest(String server, int port, String dbname, DatabaseType dbtype) {
		this(OperationType.NOOP, server, port, dbname, dbtype);
	}

	public DatabaseRequest(OperationType type, String server, int port, String dbname, DatabaseType dbtype) {
		this.server = server;
		this.dbtype = dbtype;
		this.dbname = dbname;
		this.port = port;
		this.type = type;
	}

	public boolean lessThan(DatabaseRequest o) {
		return compareTo(o) < 0;
	}

	public boolean greatherThan(DatabaseRequest o) {
		return compareTo(o) > 0;
	}

	public int compareTo(DatabaseRequest o) {
		return Long.compare(timestamp, o.timestamp);
	}

	public String getDatabaseName() {
		return dbname;
	}

	public DatabaseType getDatabaseType() {
		return dbtype;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public OperationType getType() {
		return type;
	}

	public boolean addArgument(Serializable e) {
		return arguments.add(e);
	}

	public boolean removeArgument(Serializable e) {
		return arguments.remove(e);
	}

	public boolean containsArgument(Serializable e) {
		return arguments.contains(e);
	}

	public List<Serializable> getArguments() {
		return arguments;
	}

	public Serializable getArgument(int i) {
		return arguments.get(i);
	}

	public int countArguments() {
		return arguments.size();
	}

	public void clearArguments() {
		arguments.clear();
	}

	@Override
	public String toString() {
		return "DatabaseRequest [timestamp=" + timestamp + ", type=" + type + ", dbtype=" + dbtype + ", dbname="
				+ dbname + ", arguments=" + arguments + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arguments.hashCode();
		result = prime * result + ((dbname == null) ? 0 : dbname.hashCode());
		result = prime * result + port;
		result = prime * result + ((server == null) ? 0 : server.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseRequest other = (DatabaseRequest) obj;
		if (!arguments.equals(other.arguments))
			return false;
		if (dbname == null) {
			if (other.dbname != null)
				return false;
		} else if (!dbname.equals(other.dbname))
			return false;
		if (port != other.port)
			return false;
		if (server == null) {
			if (other.server != null)
				return false;
		} else if (!server.equals(other.server))
			return false;
		if (timestamp != other.timestamp)
			return false;
		return type == other.type;
	}

	public void close() {
		arguments.clear();
	}

	public <R extends Serializable> R send() {

		Socket socket = null;
		timestamp = System.currentTimeMillis();

		try {

			socket = new Socket(server, port);

			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

			LoggerUtils.debug(getClass(), "Sended: " + this);

			// client send data object
			os.writeObject(this);

			// client received data object
			DatabaseResponse response = unwrap(is.readObject(), DatabaseResponse.class);

			// handle result
			LoggerUtils.debug(getClass(), "Received: " + response);

			os.close();
			is.close();

			if (response != null) {
				return response.get();
			}

		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		} catch (ClassNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.CLASS_NOT_FOUND, e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// close silently
				}
			}
		}

		return null;
	}

}
