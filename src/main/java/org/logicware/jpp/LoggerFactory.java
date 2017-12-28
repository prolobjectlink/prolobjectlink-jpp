package org.logicware.jpp;

public final class LoggerFactory {

	private LoggerFactory() {
	}

	public static Logger getLogger(String name) {
		// TODO
		return null;
	}

	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

}
