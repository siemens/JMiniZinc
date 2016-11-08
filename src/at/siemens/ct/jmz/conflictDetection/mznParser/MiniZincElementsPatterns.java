package at.siemens.ct.jmz.conflictDetection.mznParser;

public final class MiniZincElementsPatterns {

	//todo: Do not use this class any more
	private final static String VARIABLE_NAME_PATTERN = "([A-Za-z][A-Za-z0-9_]*)";
	private final static String INSTANTIATION_PATTERN = "(var|par)?";
	private final static String COLON = ":";
	private final static String SEMICOLON = ";";
	private final static String TYPE_INT_PATTERN = "(int)";
	private final static String TYPE_BOOL_PATTERN = "(bool)";
	private final static String ALLOW_SPACES_PATTERN = " *";
	private final static String BOOLEEAN_DEFAULT_VALUE_PATTERN = "[=]?" + ALLOW_SPACES_PATTERN + "(true|false)?"
			+ ALLOW_SPACES_PATTERN;
	private final static String INTEGER_DEFAULT_VALUE_PATTERN = "[=]?" + ALLOW_SPACES_PATTERN + "(-?\\d+)?"
			+ ALLOW_SPACES_PATTERN;
	private static final String RANGE_EXPRESSION_PATTERN = "((?:[a-zA-Z]+|\\d+) *[.]+ *(?:[a-zA-Z]+|\\d+))";

	public static final String BOOLEEAN_DECLARATION_PATTERN = INSTANTIATION_PATTERN + ALLOW_SPACES_PATTERN
			+ TYPE_BOOL_PATTERN + ALLOW_SPACES_PATTERN + COLON + ALLOW_SPACES_PATTERN + VARIABLE_NAME_PATTERN
			+ ALLOW_SPACES_PATTERN + BOOLEEAN_DEFAULT_VALUE_PATTERN + SEMICOLON;

	public static final String INTEGGER_DECLARATION_PATTERN = INSTANTIATION_PATTERN + ALLOW_SPACES_PATTERN
			+ TYPE_INT_PATTERN + ALLOW_SPACES_PATTERN + COLON + ALLOW_SPACES_PATTERN + VARIABLE_NAME_PATTERN
			+ ALLOW_SPACES_PATTERN + INTEGER_DEFAULT_VALUE_PATTERN + SEMICOLON;

	public static final String INTEGGER_WITH_RANGE_DECLARATION_PATTERN = INSTANTIATION_PATTERN + ALLOW_SPACES_PATTERN
			+ RANGE_EXPRESSION_PATTERN + ALLOW_SPACES_PATTERN + COLON + ALLOW_SPACES_PATTERN + VARIABLE_NAME_PATTERN
			+ ALLOW_SPACES_PATTERN + INTEGER_DEFAULT_VALUE_PATTERN + SEMICOLON;

}
