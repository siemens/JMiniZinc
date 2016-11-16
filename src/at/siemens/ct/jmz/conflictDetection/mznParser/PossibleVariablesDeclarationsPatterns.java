package at.siemens.ct.jmz.conflictDetection.mznParser;

public enum PossibleVariablesDeclarationsPatterns {
	BOOLEEAN_PATTERN("(var|par)? *(bool) *: *([A-Za-z][A-Za-z0-9_]*) *[=]? *(true|false)? *;"), 
	INTEGER_PATTERN("(var|par)? *(int) *: *([A-Za-z][A-Za-z0-9_]*) *[=]? *(-?\\d+)? *;"), 
	INTEGER_WITH_RANGE_PATTERN("(var|par)? *((?:[a-zA-Z]+|\\d+) *[.]+ *(?:[a-zA-Z]+|\\d+)) *: *([A-Za-z][A-Za-z0-9_]*) *[=]? *(-?\\d+)? *;");
	private String pattern;

	private PossibleVariablesDeclarationsPatterns(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

}
