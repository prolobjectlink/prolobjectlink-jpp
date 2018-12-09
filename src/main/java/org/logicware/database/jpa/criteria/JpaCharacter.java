package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;

public class JpaCharacter extends JpaExpression<Character> implements Expression<Character> {

	protected final Character character;

	public JpaCharacter(Character character) {
		super("", Character.class, null, null);
		this.character = character;
	}

	@Override
	public String toString() {
		return "" + character + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((character == null) ? 0 : character.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaCharacter other = (JpaCharacter) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character)) {
			return false;
		}
		return true;
	}

}
