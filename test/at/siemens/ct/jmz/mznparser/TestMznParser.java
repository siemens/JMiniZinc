/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.mznparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import at.siemens.ct.jmz.expressions.bool.BasicBoolean;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.integer.BasicInteger;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * @author © Siemens AG, 2016
 */
public class TestMznParser {

	MiniZincCP constraintProblem;

	@Test
	public void testMznParser() throws Exception {
		File miniZincFile = new File("testFiles\\testConflictDetection4.mzn");
		constraintProblem = new MiniZincCP(miniZincFile);
		int noOfVar = constraintProblem.getElementsFromFile().size();
		//assertEquals(noOfVar, 9);

		Displayable x_1 = constraintProblem.getDecisionVariableByName("x_1");
		Displayable x2 = constraintProblem.getDecisionVariableByName("x2");
		Displayable x3 = constraintProblem.getDecisionVariableByName("x3");
		Displayable x4 = constraintProblem.getDecisionVariableByName("x4");
		Displayable x5 = constraintProblem.getDecisionVariableByName("x5");
		Displayable x6 = constraintProblem.getDecisionVariableByName("x6");
		Displayable x7 = constraintProblem.getDecisionVariableByName("x7");

		assertTrue(x_1 != null);
		assertTrue(x2 != null);
		assertTrue(x3 != null);
		assertTrue(x4 != null);
		assertTrue(x5 == null);
		assertTrue(x6 != null);
		assertTrue(x7 != null);

		assertTrue(x_1 instanceof IntegerVariable);
		assertTrue(x2 instanceof IntegerVariable);
		assertTrue(x3 instanceof BooleanVariable);
		assertTrue(x4 instanceof IntegerVariable);

	}

	@Test
	public void testRegularExpressionForBool()

	{

		Pattern pattern = Pattern.compile(PossibleVariablesDeclarationsPatterns.BOOLEEAN_PATTERN.getPattern());

		BasicBoolean boolCt = new BooleanConstant(false).toNamedConstant("isTrue");
		String inputbooleanDeclaration = boolCt.declare();
		Matcher matcher = pattern.matcher(inputbooleanDeclaration);
		boolean match = matcher.matches();
		assertTrue(match);
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.NAME), "isTrue");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.TYPE), "bool");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.INSTANTIATION), null);
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.DEFAULT_VALUE), "false");

		BooleanVariable boolVar = new BooleanVariable("my_boolean_variable");
		inputbooleanDeclaration = boolVar.declare();
		matcher = pattern.matcher(inputbooleanDeclaration);
		match = matcher.matches();
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.NAME), "my_boolean_variable");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.TYPE), "bool");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.INSTANTIATION), "var");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.DEFAULT_VALUE), null);
		assertTrue(match);

	}

	@Test
	public void testRegularExpressionForIntegger()

	{
		BasicInteger intCt = new IntegerConstant(3).toNamedConstant("my_integer_value");
		String inputIntDeclaration = intCt.declare();

		Pattern pattern = Pattern.compile(PossibleVariablesDeclarationsPatterns.INTEGER_PATTERN.getPattern());
		Matcher matcher = pattern.matcher(inputIntDeclaration);
		boolean match = matcher.matches();
		assertTrue(match);
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.NAME), "my_integer_value");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.TYPE), "int");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.INSTANTIATION), null);
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.DEFAULT_VALUE), "3");

		pattern = Pattern.compile(PossibleVariablesDeclarationsPatterns.INTEGER_WITH_RANGE_PATTERN.getPattern());
		IntegerVariable intVar = new IntegerVariable("myIntDeclararion", new RangeExpression(1, 3));
		String inputIntDeclaration2 = intVar.declare();
		matcher = pattern.matcher(inputIntDeclaration2);
		Boolean match1 = matcher.matches();
		assertTrue(match1);
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.NAME), "myIntDeclararion");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.TYPE), "1..3");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.INSTANTIATION), "var");
		assertEquals(matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.DEFAULT_VALUE), null);

	}

	@Test
	public void testRegularExpressionForArrays() {

		String inputIntDeclaration = "array[1..3] of int: h2 = [clara, bestman, ted, alice, ron];";
		Pattern pattern = Pattern.compile(PossibleVariablesDeclarationsPatterns.ARRAY_PATTERN.getPattern());
		Matcher matcher = pattern.matcher(inputIntDeclaration);
		boolean match = matcher.matches();
		assertTrue(match);
		String array_index = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.ARRAY_INDEX_TYPE);
		assertEquals("1..3", array_index);
		String GroupName2 = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.INSTANTIATION);
		assertEquals(null, GroupName2);
		String GroupName3 = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.NAME);
		assertEquals("h2", GroupName3);
		String GroupName4 = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.DEFAULT_VALUE);
		assertEquals("clara, bestman, ted, alice, ron", GroupName4);
		String GroupName5 = matcher.group(PossibleVariablesDeclarationsPatterns.GroupNames.TYPE);
		assertEquals("int", GroupName5);
	}
}
