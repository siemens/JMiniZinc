package at.siemens.ct.jmz.conflictDetection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.conflictDetection.mznParser.MiniZincCP;
import at.siemens.ct.jmz.conflictDetection.mznParser.MiniZincElementsPatterns;
import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;
import at.siemens.ct.jmz.executor.Executor;
import at.siemens.ct.jmz.executor.IExecutor;
import at.siemens.ct.jmz.executor.PipedMiniZincExecutor;
import at.siemens.ct.jmz.expressions.bool.BasicBoolean;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.integer.BasicInteger;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.writer.IModelWriter;
import at.siemens.ct.jmz.writer.ModelWriter;

public class _TestMznParser {

	ModelBuilder modelBuilder;
	MiniZincCP constraintProblem;

	@Test
	public void testMznParser() throws IllegalArgumentException, IllegalAccessException, IOException {
		File miniZincFile = new File("testFiles\\testConflictDetection4.mzn");
		constraintProblem = new MiniZincCP(miniZincFile);
		int noOfVar = constraintProblem.getModelBuilder().elements().toArray().length;
		assertEquals(noOfVar, 7);
		TypeInst<?, ?> nc = constraintProblem.getModelBuilder().getElementByName("nc");
		TypeInst<?, ?> min = constraintProblem.getModelBuilder().getElementByName("min");
		TypeInst<?, ?> max = constraintProblem.getModelBuilder().getElementByName("max");

		TypeInst<?, ?> x_1 = constraintProblem.getModelBuilder().getElementByName("x_1");
		TypeInst<?, ?> x2 = constraintProblem.getModelBuilder().getElementByName("x2");
		TypeInst<?, ?> x3 = constraintProblem.getModelBuilder().getElementByName("x3");
		TypeInst<?, ?> x4 = constraintProblem.getModelBuilder().getElementByName("x4");

		assertTrue(nc != null);
		assertTrue(min != null);
		assertTrue(max != null);
		assertTrue(x_1 != null);
		assertTrue(x2 != null);
		assertTrue(x3 != null);
		assertTrue(x4 != null);

		assertTrue(nc instanceof BasicTypeInst<?>);
		assertTrue(min instanceof BasicTypeInst<?>);
		assertTrue(max instanceof BasicTypeInst<?>);
		assertTrue(x_1 instanceof IntegerVariable);
		assertTrue(x2 instanceof IntegerVariable);
		assertTrue(x3 instanceof BooleanVariable);
		assertTrue(x4 instanceof IntegerVariable);

	}

	// test to check if solver has the same output for a model from a .mzn file
	// and a model created programmatically
	@Test
	public void testMznParserWithSolver() throws IOException, InterruptedException, IllegalArgumentException,
			IllegalAccessException, URISyntaxException {

		// create a model from a mznFile
		File miniZincFile = new File("testFiles\\testConflictDetection4.mzn");
		constraintProblem = new MiniZincCP(miniZincFile);
		IModelWriter modelWriterCP = new ModelWriter(constraintProblem.getModelBuilder());
		modelWriterCP.setSolvingStrategy(SolvingStrategy.SOLVE_SATISFY);
		IExecutor executorCP = new PipedMiniZincExecutor("testCP", modelWriterCP);
		executorCP.startProcess();
		System.out.println("Solver.IsRunning = " + Executor.isRunning());
		executorCP.waitForSolution();
		String lastSolverOutputCP = executorCP.getLastSolverOutput();
		System.out.println(lastSolverOutputCP);

		// model creation programmatically(the same as in exercise1.mzn)
		BasicInteger nc = new IntegerConstant(3).toNamedConstant("nc");
		BasicInteger min = new IntegerConstant(4).toNamedConstant("min");
		BasicInteger max = new IntegerConstant(5).toNamedConstant("max");

		IntegerVariable x_1 = new IntegerVariable("x_1", new RangeExpression(1, nc));
		IntegerVariable x2 = new IntegerVariable("x2", new RangeExpression(min, max));
		BooleanVariable x3 = new BooleanVariable("x3");
		IntegerVariable x4 = new IntegerVariable("x4");

		modelBuilder = new ModelBuilder();
		modelBuilder.add(nc, min, max, x_1, x2, x3, x4);
		IModelWriter modelWriter = new ModelWriter(modelBuilder);
		modelWriter.setSolvingStrategy(SolvingStrategy.SOLVE_SATISFY);
		IExecutor executor = new PipedMiniZincExecutor("test", modelWriter);
		executor.startProcess();
		System.out.println("Solver.IsRunning = " + Executor.isRunning());
		executor.waitForSolution();
		String lastSolverOutput = executor.getLastSolverOutput();
		System.out.println(lastSolverOutput);

		assertEquals(lastSolverOutput, lastSolverOutputCP);
	}

	@Test
	public void testRegularExpressionForBool()

	{

		Pattern pattern = Pattern.compile(MiniZincElementsPatterns.BOOLEEAN_DECLARATION_PATTERN);

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

		Pattern pattern = Pattern.compile(MiniZincElementsPatterns.INTEGGER_DECLARATION_PATTERN);
		Matcher matcher = pattern.matcher(inputIntDeclaration);
		boolean match = matcher.matches();
		assertTrue(match);

		pattern = Pattern.compile(MiniZincElementsPatterns.INTEGGER_WITH_RANGE_DECLARATION_PATTERN);
		IntegerVariable intVar = new IntegerVariable("myIntDeclararion", new RangeExpression(1, 3));

		String inputIntDeclaration2 = intVar.declare();

		System.out.println(inputIntDeclaration2);

		matcher = pattern.matcher(inputIntDeclaration2);
		Boolean match1 = matcher.matches();
		assertTrue(match1);

	}
}
