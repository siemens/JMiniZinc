package at.siemens.ct.jmz.conflictDetection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import junit.framework.TestCase;

public class _TestSimpleConflictDetection extends TestCase {

	public void testNoMinimalConflictSet() throws Exception{
		List<Constraint> minCS = null;
		try{			
			File f = new File ("testFiles\\testConflictDetection1.mzn");
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(f.getAbsolutePath());
			
			// Define some additional constraints 
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
						
			Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
			IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
			IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
			
			BooleanExpression expression1 = new RelationalExpression<>(x1, RelationalOperator.EQ, new IntegerConstant(3));			
			BooleanExpression expression2 = new RelationalExpression<>(x2, RelationalOperator.EQ, new IntegerConstant(3));			
			
			Constraint constraint = new Constraint("group", "x1 = 3", expression1);
			constraintsSetC.add(constraint);
			
			constraint = new Constraint("group", "x2 = 3", expression2);
			constraintsSetC.add(constraint);
						
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNull(minCS);			
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
		assertNull(minCS);
	}
	
	public void testExistsMinimalConflictSet() throws Exception{
		List<Constraint> minCS = null;
		try{									
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC);
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
			assertTrue(minCS.contains(constraintsSetC.get(1)));
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}		
	}
}
