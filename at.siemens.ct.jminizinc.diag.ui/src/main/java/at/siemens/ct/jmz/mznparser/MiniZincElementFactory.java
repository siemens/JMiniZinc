/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.mznparser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.siemens.ct.common.utils.StringUtils;
import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.expressions.bool.BasicBoolean;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.integer.BasicInteger;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

public class MiniZincElementFactory {

	private final String BOOL_TYPE = "bool";
	private final String VAR_TYPE = "var";
	private final String INT_TYPE = "int";
	private static final String RANGE_SEPARATOR = "..";
	private List<BasicTypeInst<?>> listWithParameters = new ArrayList<BasicTypeInst<?>>();

	public Displayable getElementFromLine(String line) throws Exception {

		String instantiation, name, type, defaultValue, arrayIndex;
		for (PossibleVariablesDeclarationsPatterns patternAsString : PossibleVariablesDeclarationsPatterns.values()) {
			Pattern pattern = Pattern.compile(patternAsString.getPattern());
			String trimmedLine = line.trim();
			Matcher matcher = pattern.matcher(trimmedLine);
			if (matcher.matches()) {
				instantiation = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.INSTANTIATION);
				type = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.TYPE);
				name = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.NAME);
				defaultValue = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.DEFAULT_VALUE);

				try {
					arrayIndex = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.ARRAY_INDEX_TYPE);
				} catch (Exception e) {
					arrayIndex = null;
				}

				if (arrayIndex != null) {
					RangeExpression arraySize;
					if (arrayIndex.contains(RANGE_SEPARATOR)) {
						arraySize = createRange(arrayIndex);
					} else {
						throw new Exception("Unimplemented! define array range as m..n!");
					}

					if (instantiation != null && instantiation.equals(VAR_TYPE) && defaultValue == null) {
						if (type.equals(BOOL_TYPE)) {
							return new DisplayableBooleanArray(name, arraySize);
						}
						if (type.equals(INT_TYPE)) {
							return new DisplayableIntegerArray(name, arraySize);
						}
						if (type.contains(RANGE_SEPARATOR)) {
							RangeExpression values = createRange(type);
							return new DisplayableIntegerArray(name, arraySize, values);
						}
					} else {
						//TODO: add to parameters list ?
						return null;
					}

				}

				if (type.equals(BOOL_TYPE)) {
					if (instantiation != null && instantiation.equals(VAR_TYPE) && defaultValue == null) {
						return new DisplayableBooleanVariable(name);
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
					}
				}
				if (type.equals(INT_TYPE)) {
					if (instantiation != null && instantiation.equals(VAR_TYPE) && defaultValue == null) {
						return new DisplayableIntegerVariable(name);
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
					}
				}
				if (type.contains(RANGE_SEPARATOR)) {
					return new DisplayableIntegerVariable(name, createRange(type));
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

	public static Boolean isNumeric(String str) {
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

	private RangeExpression createRange(String range) {
		String min, max;
		IntegerExpression lb;
		IntegerExpression ub;
		min = StringUtils.removePostfix(range, RANGE_SEPARATOR);
		max = StringUtils.removePrefix(range, RANGE_SEPARATOR);

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

		return new RangeExpression(lb, ub);

	}
}
