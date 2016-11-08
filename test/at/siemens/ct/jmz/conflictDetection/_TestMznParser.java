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
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;
import at.siemens.ct.jmz.executor.Executor;
import at.siemens.ct.jmz.executor.IExecutor;
import at.siemens.ct.jmz.executor.PipedMiniZincExecutor;
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
	public void testMznParser() throws IOException, InterruptedException, IllegalArgumentException,
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
	public void testMinimalConflict()
			throws IOException, InterruptedException, IllegalArgumentException, IllegalAccessException {
		/*
		 * NamedIntegerConstant nc = new
		 * IntegerConstant(3).toNamedConstant("nc"); NamedConstantSet<Integer>
		 * variableDomain = new RangeExpression(1,
		 * nc).toNamedConstant("variableDomain");
		 */
		// IntSet variableDomain = new IntSet("variableDomain", 1, 3);
		// IntVar x1 = new IntVar("x1", variableDomain);
		// IntVar x2 = new IntVar("x2", variableDomain);
		// IntVar x3 = new IntVar("x3", variableDomain);

		// BooleanExpression c1 = new RelationalExpression<>(x1,
		// RelationalOperator.EQ, new IntConstant(1));
		// BooleanExpression c2 = new RelationalExpression<>(x1,
		// RelationalOperator.EQ, new IntConstant(2));
		// BooleanExpression c3 = new RelationalExpression<>(x2,
		// RelationalOperator.EQ, x1);
		// BooleanExpression c4 = new RelationalExpression<>(x3,
		// RelationalOperator.EQ, x2);
		// BooleanExpression c5 = new RelationalExpression<>(x3,
		// RelationalOperator.GT, new IntConstant(2));
		//
		// Constraint constraint1 = new Constraint("test", "c1", c1);
		// Constraint constraint2 = new Constraint("test", "c2", c2);
		// Constraint constraint3 = new Constraint("test", "c3", c3);
		// Constraint constraint4 = new Constraint("test", "c4", c4);
		// Constraint constraint5 = new Constraint("test", "c5", c5);

		File miniZincFile = new File("testFiles\\testConflictDetection3.mzn");
		modelBuilder = new ModelBuilder();

		// modelBuilder.add(/* nc, */variableDomain, x1, x2, x3
		/* constraint1,constraint2,constraint3,constraint4, constraint5 */// );
		IModelWriter modelWriter = new ModelWriter(modelBuilder);
		modelWriter.setSolvingStrategy(SolvingStrategy.SOLVE_SATISFY);
		IExecutor executor = new PipedMiniZincExecutor("test", modelWriter);
		executor.startProcess();
		System.out.println("Solver.IsRunning = " + Executor.isRunning());
		executor.waitForSolution();

		String lastSolverOutput = executor.getLastSolverOutput();

		System.out.println(lastSolverOutput);

		constraintProblem = new MiniZincCP(miniZincFile);
		IModelWriter modelWriterCP = new ModelWriter(constraintProblem.getModelBuilder());
		modelWriterCP.setSolvingStrategy(SolvingStrategy.SOLVE_SATISFY);
		IExecutor executorCP = new PipedMiniZincExecutor("testCP", modelWriterCP);
		executorCP.startProcess();
		System.out.println("Solver.IsRunning = " + Executor.isRunning());
		executorCP.waitForSolution();

		String lastSolverOutputCP = executorCP.getLastSolverOutput();

		assertEquals(lastSolverOutput, lastSolverOutputCP);

	}

	@Test
	public void testRegularExpressionForBool()

	{
		// BooleanConstant boolCt = new BooleanConstant(true);
		// String inputbooleanDeclaration = boolCt.declare();
		//
		Pattern pattern = Pattern.compile(MiniZincElementsPatterns.BOOLEEAN_DECLARATION_PATTERN);
		// Matcher matcher = pattern.matcher(inputbooleanDeclaration);
		// boolean match = matcher.matches();
		// assertTrue(match);
		//
		// boolCt = new BooleanConstant("isTrue", false);
		// inputbooleanDeclaration = boolCt.declare();
		// matcher = pattern.matcher(inputbooleanDeclaration);
		// match = matcher.matches();
		// assertTrue(match);

		BooleanVariable boolVar = new BooleanVariable("my_boolean_variable");
		String inputbooleanDeclaration = boolVar.declare();
		Matcher matcher = pattern.matcher(inputbooleanDeclaration);
		boolean match = matcher.matches();
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
	
	@Test
	public void testIsConsistent() throws URISyntaxException, IllegalArgumentException, IllegalAccessException, IOException, InterruptedException
	{
		File miniZincFile = new File("testFiles\\testConflictDetection3.mzn");
		constraintProblem = new MiniZincCP(miniZincFile);
		
		//Boolean failed = ConflictDetection.isConsistent(modelBuilder);
		
		//assertTrue(!failed);
		
		
		
	}
	

}
