/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.querylang.jpql;

import org.prolobjectlink.db.querylang.SymbolEntry;
import org.prolobjectlink.prolog.AbstractIterator;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpqlSymbols extends AbstractIterator<SymbolEntry> {
	public static final int AS = 39;
	public static final int LEFT = 88;
	public static final int COALESCE = 8;
	public static final int TIMES = 25;
	public static final int FROM = 33;
	public static final int GT = 20;
	public static final int POSITIVE = 97;
	public static final int TIMESTAMP = 101;
	public static final int TYPE = 60;
	public static final int OUTER = 90;
	public static final int GROUP = 35;
	public static final int ASC = 41;
	public static final int GEQ = 21;
	public static final int UNEQ = 22;
	public static final int TREAT = 45;
	public static final int LIKE = 50;
	public static final int COMMA = 11;
	public static final int SUM = 83;
	public static final int JOIN = 91;
	public static final int INNER = 89;
	public static final int ALL = 51;
	public static final int BOTH = 81;
	public static final int FALSE = 99;
	public static final int MEMBER = 57;
	public static final int NOT = 16;
	public static final int LE = 18;
	public static final int OBJECT = 43;
	public static final int MIN = 85;
	public static final int THEN = 6;
	public static final int TRIM = 76;
	public static final int EQ = 17;
	public static final int LOWER = 77;
	public static final int EMPTY = 56;
	public static final int MOD = 68;
	public static final int LEADING = 79;
	public static final int WHEN = 5;
	public static final int SELECT = 29;
	public static final int NUMBER = 94;
	public static final int UPLUS = 28;
	public static final int TRUE = 98;
	public static final int CURRENT_TIMESTAMP = 73;
	public static final int PLUS = 24;
	public static final int PERCENT = 104;
	public static final int QUESTION = 62;
	public static final int INDEX = 70;
	public static final int EXISTS = 59;
	public static final int ORDER = 38;
	public static final int LPAR = 12;
	public static final int NULLIF = 9;
	public static final int DELETE = 30;
	public static final int CHAR = 95;
	public static final int ESCAPE = 54;
	public static final int DIV = 26;
	public static final int MAX = 84;
	public static final int CONCAT = 74;
	public static final int ELSE = 7;
	public static final int DOT = 10;
	public static final int LENGTH = 64;
	public static final int ENTRY = 46;
	public static final int AVG = 82;
	public static final int NULL = 102;
	public static final int EOF = 0;
	public static final int BETWEEN = 49;
	public static final int CURRENT_DATE = 71;
	public static final int LOCATE = 65;
	public static final int WHERE = 34;
	public static final int RPAR = 13;
	public static final int COUNT = 86;
	public static final int FUNCTION = 2;
	public static final int UNDERSCORE = 103;
	public static final int TRAILING = 80;
	public static final int IS = 55;
	public static final int MINUS = 23;
	public static final int FETCH = 87;
	public static final int DATE = 100;
	public static final int IN = 61;
	public static final int SUBSTRING = 75;
	public static final int OR = 14;
	public static final int SET = 40;

	//
	public static final int ERROR = 1;

	//
	public static final int ON = 92;
	public static final int DESC = 42;
	public static final int ID = 93;
	public static final int OF = 58;
	public static final int ABS = 66;
	public static final int END = 3;
	public static final int COLON = 63;
	public static final int BY = 36;
	public static final int ANY = 52;
	public static final int SOME = 53;
	public static final int CASE = 4;
	public static final int SQRT = 67;
	public static final int NEW = 44;
	public static final int UPDATE = 31;
	public static final int STRING = 96;
	public static final int SIZE = 69;
	public static final int UPPER = 78;
	public static final int ENTITY = 105;
	public static final int AND = 15;
	public static final int VALUE = 48;
	public static final int CURRENT_TIME = 72;
	public static final int UMINUS = 27;
	public static final int KEY = 47;
	public static final int HAVING = 37;
	public static final int DISTINCT = 32;
	public static final int LEQ = 19;

	public static final int INTEGER_LITERAL = 106;
	public static final int CHARACTER_LITERAL = 107;
	public static final int FLOATING_POINT_LITERAL = 108;
	public static final int IDENTIFIER = 109;
	public static final int STRING_LITERAL = 110;

	protected JpqlSymbols() {
	}

}
