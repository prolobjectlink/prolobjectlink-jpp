package org.logicware.database;

public interface ObjectSerializer<T> {

	public Object toObject(T prologTerm);

	public T toTerm(Object object);

}
