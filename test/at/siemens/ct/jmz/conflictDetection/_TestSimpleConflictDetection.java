package at.siemens.ct.jmz.conflictDetection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import junit.framework.TestCase;

public class _TestSimpleConflictDetection extends TestCase {

	public void testSimpleConflictDetection_1() throws Exception {
		List<Constraint> minCS = null;
		try {
			File f = new File("testFiles\\testConflictDetection1.mzn");
			// Define some additional constraints
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();

			Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
			IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
			IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);
			IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);
			List<Element> decisionsVariable = new ArrayList<Element>();

			decisionsVariable.add(setOneTwoThree);
			decisionsVariable.add(x1);
			decisionsVariable.add(x2);
			decisionsVariable.add(x3);

			BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ,
					new IntegerConstant(3));
			BooleanExpression expression2 = new RelationalOperation<>(x2, RelationalOperator.EQ,
					new IntegerConstant(3));

			Constraint constraint = new Constraint("group", "x1 = 3", expression1);
			constraintsSetC.add(constraint);

			constraint = new Constraint("group", "x2 = 3", expression2);
			constraintsSetC.add(constraint);

			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(f.getAbsolutePath());
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNull(minCS);
			DebugUtils.printConstraintsSet("testSimpleConflictDetection_1:", minCS);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		assertNull(minCS);
	}
	
	public void testSimpleConflictDetection_NoConflict() throws Exception{
		List<Constraint> minCS = null;
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>(); 
			String fileName = UtilsForTest.getTestDataset2NoConflict(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNull(minCS);			
			DebugUtils.printConstraintsSet("testSimpleConflictDetection_Subset2:", minCS);
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}

	public void testSimpleConflictDetection_2() throws Exception {
		List<Constraint> minCS = null;
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
			assertTrue(minCS.contains(constraintsSetC.get(1)));
			DebugUtils.printConstraintsSet("testSimpleConflictDetection_2:", minCS);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public void testSimpleConflictDetection_5() throws Exception {
		List<Constraint> minCS = null;
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset5(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testSimpleConflictDetection_5:", minCS);

			assertTrue(minCS.size() == 1);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public void testSimpleConflictDetection_6() throws Exception {
		List<Constraint> minCS = null;
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testSimpleConflictDetection_6:", minCS);

			assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
			assertTrue(minCS.contains(constraintsSetC.get(1)));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public void testSimpleConflictDetection_7() throws Exception {
		List<Constraint> minCS = null;
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset7(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testSimpleConflictDetection_7:", minCS);
			assertTrue(minCS.size() == 3);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
			assertTrue(minCS.contains(constraintsSetC.get(1)));
			assertTrue(minCS.contains(constraintsSetC.get(3)));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public void testSimpleConflictDetection_8() throws Exception {
		List<Constraint> minCS = null;
		try {
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testSimpleConflictDetection_8:", minCS);

			assertTrue(minCS.size() == 1);
			assertTrue(minCS.contains(constraintsSetC.get(0)));

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
