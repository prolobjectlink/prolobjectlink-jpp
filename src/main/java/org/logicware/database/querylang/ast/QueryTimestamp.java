package org.logicware.database.querylang.ast;

import org.logicware.database.querylang.AbstractTimestamp;
import org.logicware.database.querylang.Timestamp;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public class QueryTimestamp extends AbstractTimestamp implements Timestamp {

	public QueryTimestamp(long timestamp) {
		super(timestamp);
	}

}
