package at.siemens.ct.jmz.conflictDetection.mznParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.siemens.ct.common.utils.StringUtils;
import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.expressions.bool.BasicBoolean;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.integer.BasicInteger;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

//todo: Do not use Generics!!!!
public class MiniZincElementFactory<T> {

	private final int INSTANTIATION_INDEX = 1;
	private final int TYPE_INDEX = 2;
	private final int NAME_INDEX = 3;
	private final int DEFAULT_VALUE_INDEX = 4;

	List<String> possiblePatterns;

	public MiniZincElementFactory() throws IllegalArgumentException, IllegalAccessException {

		possiblePatterns = new ArrayList<>();
		for (Field f : MiniZincElementsPatterns.class.getDeclaredFields()) {

			if (java.lang.reflect.Modifier.isStatic(f.getModifiers())
					&& java.lang.reflect.Modifier.isPublic(f.getModifiers())) {
				possiblePatterns.add(f.get(null).toString());
			}
		}

	}

	List<BasicTypeInst<T>> listWithParameters = new ArrayList<BasicTypeInst<T>>();

	public Element getElementFromLine(String line) {

		String instantiation, name, type, defaultValue;
		for (String patternAsString : possiblePatterns) {
			Pattern pattern = Pattern.compile(patternAsString);
			String trimmedLine = line.trim();
			Matcher matcher = pattern.matcher(trimmedLine);
			if (matcher.matches()) {

				instantiation = matcher.group(INSTANTIATION_INDEX);
				type = matcher.group(TYPE_INDEX);
				name = matcher.group(NAME_INDEX);
				defaultValue = matcher.group(DEFAULT_VALUE_INDEX);

				if (type.equals("bool")) {
					if (instantiation.equals("var") && instantiation != null) {
						return new BooleanVariable(name);
					} else {
						Boolean parameterValue = Boolean.parseBoolean(defaultValue);
						BasicBoolean boolConstant = new  BooleanConstant(parameterValue).toNamedConstant(name);
						listWithParameters.add((BasicTypeInst<T>) boolConstant);
						return boolConstant;
					}
				}

				if (type.equals("int")) {
					if (instantiation != null && instantiation.equals("var")) {
						return new IntegerVariable(name);
					} else {
						int paramValue = Integer.parseInt(defaultValue);
						BasicInteger intConstant = new IntegerConstant(paramValue).toNamedConstant(name);
						listWithParameters.add((BasicTypeInst<T>) intConstant);
						return intConstant;
					}
				}
				if (type.contains("..")) {
					String min, max;
					IntegerExpression lb ;
					IntegerExpression ub ;
					min = StringUtils.removePostfix(type, "..");
					max = StringUtils.removePrefix(type, "..");

					if (isNumeric(min)) {
						lb=   new  IntegerConstant(Integer.parseInt(min));
					} else {
						lb = (IntegerExpression) getElementByName(min).getValue();}
					
					
					if (isNumeric(max)) {
						ub =    new  IntegerConstant(Integer.parseInt(max));
					} else {
						ub = (IntegerExpression) getElementByName(max).getValue();
					}

					return new IntegerVariable(name, new RangeExpression(lb, ub));

				}

			}
		}
		return null;

	}

	private BasicTypeInst<T> getElementByName(String name) {
		for (BasicTypeInst<T> element : listWithParameters) {
			if (element.getName().equals(name)) {
				return element;
			}
		}
		return null;
	}
	
	private Boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
