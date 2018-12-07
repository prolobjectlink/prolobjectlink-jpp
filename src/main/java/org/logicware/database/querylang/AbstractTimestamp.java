package org.logicware.database.querylang;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractTimestamp extends AbstractExp implements TreeNode {

	protected long timestamp;

	public AbstractTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "" + timestamp + "";
	}

}
