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
			File f = new File ("testFiles\\testConflictDetection2.mzn");
			AbstractConflictDetection conflictDetection = new SimpleConflictDetection(f.getAbsolutePath());
			
			// Define some additional constraints 
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
						
			Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
			IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
			IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
			IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);
			
			BooleanExpression expression1 = new RelationalExpression<>(x1, RelationalOperator.EQ, new IntegerConstant(1));						
			Constraint c1 = new Constraint("group", "c1 {x1 = 1}", expression1);
			constraintsSetC.add(c1);
			
			BooleanExpression expression2 = new RelationalExpression<>(x1, RelationalOperator.EQ, new IntegerConstant(2));
			Constraint c2 = new Constraint("group", "c2 {x1 = 2}", expression2);
			constraintsSetC.add(c2);
			
			BooleanExpression expression3 = new RelationalExpression<>(x2, RelationalOperator.EQ, x1);
			Constraint c3 = new Constraint("group", "c3 {x2 = x1}", expression3);
			constraintsSetC.add(c3);
			
			BooleanExpression expression4 = new RelationalExpression<>(x3, RelationalOperator.EQ, x2);
			Constraint c4 = new Constraint("group", "c4 {x3 = x2}", expression4);
			constraintsSetC.add(c4);
			
			BooleanExpression expression5 = new RelationalExpression<>(x3, RelationalOperator.GT, new IntegerConstant(2));
			Constraint c5 = new Constraint("group", "c5 {x3 > 2}", expression5);
			constraintsSetC.add(c5);
												
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(c1));
			assertTrue(minCS.contains(c2));
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}		
	}
}
