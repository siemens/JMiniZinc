/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.mznparser;

public enum PossibleVariablesDeclarationsPatterns {

	BOOLEAN_PATTERN("(?<inst>var|par)? *(?<type>bool) *: *(?<name>[A-Za-z][A-Za-z0-9_]*) *[=]? *(?<defaultValue>true|false)? *;"),
	INTEGER_PATTERN("(?<inst>var|par)? *(?<type>int) *: *(?<name>[A-Za-z][A-Za-z0-9_]*) *[=]? *(?<defaultValue>-?\\d+)? *;"), 
	INTEGER_WITH_RANGE_PATTERN("(?<inst>var|par)? *(?<type>(?:[a-zA-Z]+|\\d+) *[.]+ *(?:[a-zA-Z]+|\\d+)) *: *(?<name>[A-Za-z][A-Za-z0-9_]*) *[=]? *(?<defaultValue>-?\\d+)? *;"),
	ARRAY_PATTERN("array\\[(?<index>int|(?:(?:[a-zA-Z]+|\\d+) *[.]+ *(?:[a-zA-Z]+|\\d+)))*\\] * of *(?<inst>var|par)? ?(?<type>int|bool|(?:[A-Za-z][A-Za-z0-9_]*)|(?:(?:[a-zA-Z]+|\\d+) *[.]+ *(?:[a-zA-Z]+|\\d+))) *: *(?<name>[A-Za-z][A-Za-z0-9_]*)* *[=]? *(\\[(?<defaultValue>([a-zA-Z0-9_, ]*)*)\\])?;"),
  ENUM_VARIABLE_PATTERN("(?<inst>var|par)? *(?<type>[A-Za-z][A-Za-z0-9_]*) *: *(?<name>[A-Za-z][A-Za-z0-9_]*) *[=]? *(?<defaultValue>[A-Za-z][A-Za-z0-9_]*)? *;");

	private String pattern;

	PossibleVariablesDeclarationsPatterns(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public static class GroupNames {
		public static final String INSTANTIATION = "inst";
		public static final String TYPE = "type";
		public static final String NAME = "name";
		public static final String DEFAULT_VALUE = "defaultValue";
		public static final String ARRAY_INDEX_TYPE = "index";
	}

}
