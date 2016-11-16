package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.NullSolvingStrategy;
import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.output.OutputAllVariables;
import at.siemens.ct.jmz.elements.solving.OptimizationType;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;
import at.siemens.ct.jmz.expressions.array.ExplicitIntegerList;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.integer.BasicInteger;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Tests {@link ModelWriter}
 *
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestModelWriter {

	private IModelBuilder modelBuilder = new ModelBuilder();
	private IModelWriter modelWriter = new ModelWriter(modelBuilder);

	@Before
	public void setUp() {
		modelBuilder.reset();
		modelWriter.setSolvingStrategy(new NullSolvingStrategy());
	}

	/**
	 * Creates a single constant, writes its declaration to a string and checks the result.
	 */
	@Test
	public void testCreateSingleConstantToString() {
		modelBuilder.add(new IntegerConstant(1).toNamedConstant("i"));
		String output = modelWriter.toString();
		Assert.assertEquals("int: i = 1;", output);
	}

	/**
	 * Creates a single integer set, writes its declaration to a temporary file and checks the result.
	 *
	 * @throws IOException
	 */
	@Test
	public void testCreateSingleSetTemporaryFile() throws IOException {
    modelBuilder.add(new RangeExpression(1, 99).toNamedConstant("s"));
		File tempFile = File.createTempFile("jmz", null);
		try {
			modelWriter.toFile(tempFile);
			String result = Files.lines(tempFile.toPath()).collect(Collectors.joining());
			Assert.assertEquals("set of int: s = 1..99;", result);
		} finally {
			tempFile.delete();
		}
	}

	/**
	 * Creates a constant and an integer set, writes their declarations to a string and checks the result.
	 */
	@Test
	public void testCreateTwoLinesToString() {
    BasicInteger i = new IntegerConstant(2).toNamedConstant("i");
    Set<Integer> s = new RangeExpression(1, i).toNamedConstant("s");
		modelBuilder.add(i, s);
		String output = modelWriter.toString();

		StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append("int: i = 2;");
		expectedOutput.append(System.lineSeparator());
		expectedOutput.append("set of int: s = 1..i;");

		Assert.assertEquals(expectedOutput.toString(), output);
	}

	/**
	 * Creates a variable whose type is the set of all integers, writes its declaration to a string and checks the result.
	 */
	@Test
	public void testCreateIntVarToString() {
		modelBuilder.add(new IntegerVariable("i"));
		String output = modelWriter.toString();
		Assert.assertEquals("var int: i;", output);
	}

	/**
	 * Creates a variable whose type is a set of integers, writes its declaration to a string and checks the result.
	 */
	@Test
	public void testCreateRestrictedIntVarToString() {
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable i = new IntegerVariable("i", setOneTwoThree);
		modelBuilder.add(setOneTwoThree, i);
		String output = modelWriter.toString();

		StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append("set of int: OneTwoThree = 1..3;");
		expectedOutput.append(System.lineSeparator());
		expectedOutput.append("var OneTwoThree: i;");

		Assert.assertEquals(expectedOutput.toString(), output);
	}

	/**
	 * Creates an array of integer constants, writes its declaration to a string and checks the result.
	 */
	@Test
	public void testCreateIntArrayConstantToString() {
		String setRangeName = "Range";
		String arrayName = "a";
    Set<Integer> setRange = new RangeExpression(1, 3).toNamedConstant(setRangeName);
		List<Integer> values = Arrays.asList(1, 2, 3);
		IntegerArray iac = IntegerArray.createConstant(arrayName,
        new ExplicitIntegerList(setRange, values));
		modelBuilder.add(setRange, iac);
		String output = modelWriter.toString();

		StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append("set of int: Range = 1..3;");
		expectedOutput.append(System.lineSeparator());
		expectedOutput.append(
				"array[" + setRangeName + "] of int: " + arrayName + " = " + values.toString() + ";");
		Assert.assertEquals(expectedOutput.toString(), output);
	}

	/**
	 * Creates an array of integer variables, writes its declaration to a string and checks the result.
	 */
	@Test
	public void testCreateIntArrayVarToString() {
		String setRangeName = "Range";
		String arrayName = "a";
    Set<Integer> setRange = new RangeExpression(1, 3).toNamedConstant(setRangeName);
		IntegerArray iav = IntegerArray.createVariable(arrayName, setRange);
		modelBuilder.add(setRange, iav);
		String output = modelWriter.toString();

		StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append("set of int: Range = 1..3;");
		expectedOutput.append(System.lineSeparator());
		expectedOutput.append(
				"array[" + setRangeName + "] of var int: " + arrayName + ";");
		Assert.assertEquals(expectedOutput.toString(), output);
	}

	/**
	 * Creates one integer variable and a minimization statement, writes their declarations to a string and checks the
	 * result.
	 */
	@Test
	public void testMinimizeToString() {
		testOptimizeToString(OptimizationType.MIN);
	}

	/**
	 * Creates one integer variable and a maximization statement, writes their declarations to a string and checks the
	 * result.
	 */
	@Test
	public void testMaximizeToString() {
		testOptimizeToString(OptimizationType.MAX);
	}

	private void testOptimizeToString(OptimizationType type) {
		IntegerVariable i = new IntegerVariable("i");
		modelBuilder.add(i);
		modelWriter.setSolvingStrategy(SolvingStrategy.optimize(type, i));

		StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append("var int: i;");
		expectedOutput.append(System.lineSeparator());
		expectedOutput.append("solve ");

		switch (type) {
		case MIN:
			expectedOutput.append("minimize");
			break;
		case MAX:
			expectedOutput.append("maximize");
			break;
		default:
			throw new IllegalArgumentException("Unknown OptimizationType: " + type);
		}

		expectedOutput.append(" i;");
		Assert.assertEquals(expectedOutput.toString(), modelWriter.toString());
	}

	/**
	 * Creates a variable whose type is the set of all integers, writes its declaration and an {@link OutputAllVariables}
	 * output statement to a string and checks the result.
	 */
	@Test
	@Ignore
	@Deprecated
	public void testCreateIntVarAndOutputStatementToString() {
		modelBuilder.add(new IntegerVariable("i"));
		modelWriter.setOutputStatement(new OutputAllVariables(modelBuilder.elements()));

		StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append("var int: i;");
		expectedOutput.append(System.lineSeparator());
		expectedOutput.append("output [\"i = \\(i);\\n\"];");
		Assert.assertEquals(expectedOutput.toString(), modelWriter.toString());
	}

	/**
	 * Creates two integer variables, writes their declarations and an {@link OutputAllVariables} output statement to a
	 * string and checks the result.
	 */
	@Test
	@Ignore
	@Deprecated
	public void testCreateTwoIntVarsAndOutputStatementToString() {
		modelBuilder.add(new IntegerVariable("i"));
		modelBuilder.add(new IntegerVariable("j"));
		modelWriter.setOutputStatement(new OutputAllVariables(modelBuilder.elements()));

		StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append("var int: i;");
		expectedOutput.append(System.lineSeparator());
		expectedOutput.append("var int: j;");
		expectedOutput.append(System.lineSeparator());
		expectedOutput.append("output [\"i = \\(i);\\n\", \"j = \\(j);\\n\"];");
		Assert.assertEquals(expectedOutput.toString(), modelWriter.toString());
	}

}
