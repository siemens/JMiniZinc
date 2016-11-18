package at.siemens.jmz.mznparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.expressions.bool.BasicBoolean;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.integer.BasicInteger;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.mznparser.MiniZincCP;
import at.siemens.ct.jmz.mznparser.PossibleVariablesDeclarationsPatterns;

public class _TestMznParser {

	ModelBuilder modelBuilder;
	MiniZincCP constraintProblem;

	@Test
	public void testMznParser() throws IllegalArgumentException, IllegalAccessException, IOException {
		File miniZincFile = new File("testFiles\\testConflictDetection4.mzn");
		constraintProblem = new MiniZincCP(miniZincFile);
		int noOfVar = constraintProblem.getElementsFromFile().size();
		assertEquals(noOfVar, 7);

		TypeInst<?, ?> x_1 = constraintProblem.getDecisionVariableByName("x_1");
		TypeInst<?, ?> x2 = constraintProblem.getDecisionVariableByName("x2");
		TypeInst<?, ?> x3 = constraintProblem.getDecisionVariableByName("x3");
		TypeInst<?, ?> x4 = constraintProblem.getDecisionVariableByName("x4");

		assertTrue(x_1 != null);
		assertTrue(x2 != null);
		assertTrue(x3 != null);
		assertTrue(x4 != null);

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

		BooleanVariable boolVar = new BooleanVariable("my_boolean_variable");
		inputbooleanDeclaration = boolVar.declare();
		matcher = pattern.matcher(inputbooleanDeclaration);
		match = matcher.matches();
		assertTrue(match);

	}

	@Test
	public void testRegularExpressionForIntegger()

	{
		BasicInteger intCt = new IntegerConstant(3).toNamedConstant("my_integer_value");
		String inputIntDeclaration = intCt.declare();

		System.out.println(inputIntDeclaration);

		Pattern pattern = Pattern.compile(PossibleVariablesDeclarationsPatterns.INTEGER_PATTERN.getPattern());
		Matcher matcher = pattern.matcher(inputIntDeclaration);
		boolean match = matcher.matches();
		assertTrue(match);

		pattern = Pattern.compile(PossibleVariablesDeclarationsPatterns.INTEGER_WITH_RANGE_PATTERN.getPattern());
		IntegerVariable intVar = new IntegerVariable("myIntDeclararion", new RangeExpression(1, 3));

		String inputIntDeclaration2 = intVar.declare();

		System.out.println(inputIntDeclaration2);

		matcher = pattern.matcher(inputIntDeclaration2);
		Boolean match1 = matcher.matches();
		assertTrue(match1);

	}
}
