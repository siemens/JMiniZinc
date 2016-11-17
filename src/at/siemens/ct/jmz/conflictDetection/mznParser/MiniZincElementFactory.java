package at.siemens.ct.jmz.conflictDetection.mznParser;

import java.util.*;
import java.util.regex.*;

import at.siemens.ct.common.utils.StringUtils;
import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.expressions.bool.*;
import at.siemens.ct.jmz.expressions.integer.*;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

public class MiniZincElementFactory {

	private final int INSTANTIATION_INDEX = 1;
	private final int TYPE_INDEX = 2;
	private final int NAME_INDEX = 3;
	private final int DEFAULT_VALUE_INDEX = 4;
	private final String BOOL_TYPE = "bool";
	private final String VAR_TYPE = "var";
	private final String INT_TYPE = "int";
	private final String RANGE_SEPARATOR = "..";
	private List<BasicTypeInst<?>> listWithParameters = new ArrayList<BasicTypeInst<?>>();

	public Element getElementFromLine(String line) {

		String instantiation, name, type, defaultValue;
		for (PossibleVariablesDeclarationsPatterns patternAsString : PossibleVariablesDeclarationsPatterns.values()) {
			Pattern pattern = Pattern.compile(patternAsString.getPattern());
			String trimmedLine = line.trim();
			Matcher matcher = pattern.matcher(trimmedLine);
			if (matcher.matches()) {

				instantiation = matcher.group(INSTANTIATION_INDEX);
				type = matcher.group(TYPE_INDEX);
				name = matcher.group(NAME_INDEX);
				defaultValue = matcher.group(DEFAULT_VALUE_INDEX);

				if (type.equals(BOOL_TYPE)) {
					if (instantiation != null && instantiation.equals(VAR_TYPE) && defaultValue == null) {
						return new BooleanVariable(name);
					} else {
						Boolean parameterValue;
						if (isBooleean(defaultValue)) {
							parameterValue = Boolean.parseBoolean(defaultValue);
						} else {
							BooleanConstant boolCt = (BooleanConstant) getElementByName(name).getValue();
							parameterValue = boolCt.getValue();
						}
						BasicBoolean boolConstant = new BooleanConstant(parameterValue).toNamedConstant(name);
						listWithParameters.add((BasicTypeInst<Boolean>) boolConstant);
						return boolConstant;
					}
				}
				if (type.equals(INT_TYPE)) {
					if (instantiation != null && instantiation.equals(VAR_TYPE) && defaultValue == null) {
						return new IntegerVariable(name);
					} else {
						int paramValue;
						if (isNumeric(defaultValue)) {
							paramValue = Integer.parseInt(defaultValue);
						} else {
							IntegerConstant boolCt = (IntegerConstant) getElementByName(name).getValue();
							paramValue = boolCt.getValue();
						}
						BasicInteger intConstant = new IntegerConstant(paramValue).toNamedConstant(name);
						listWithParameters.add((BasicTypeInst<Integer>) intConstant);
						return intConstant;
					}
				}
				if (type.contains(RANGE_SEPARATOR)) {
					String min, max;
					IntegerExpression lb;
					IntegerExpression ub;
					min = StringUtils.removePostfix(type, RANGE_SEPARATOR);
					max = StringUtils.removePrefix(type, RANGE_SEPARATOR);

					if (isNumeric(min)) {
						lb = new IntegerConstant(Integer.parseInt(min));
					} else {
						lb = (IntegerExpression) getElementByName(min).getValue();
					}
					if (isNumeric(max)) {
						ub = new IntegerConstant(Integer.parseInt(max));
					} else {
						ub = (IntegerExpression) getElementByName(max).getValue();
					}
					return new IntegerVariable(name, new RangeExpression(lb, ub));
				}
			}
		}
		return null;
	}

	private BasicTypeInst<?> getElementByName(String name) {
		for (BasicTypeInst<?> element : listWithParameters) {
			if (element.getName().equals(name)) {
				return element;
			}
		}
		return null;
	}

	public static  Boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static Boolean isBooleean(String str) {
		if (str.equals("true") || str.equals("false"))
			return true;
		return false;
	}
}
