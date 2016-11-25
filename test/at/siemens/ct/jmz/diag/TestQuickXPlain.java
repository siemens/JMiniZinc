/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * @author © Siemens AG, 2016
 */
public class TestQuickXPlain {
	
	@Test
	public void testConsistencyChecker_2() throws Exception{
		ConsistencyChecker checker = new ConsistencyChecker();
		List<Constraint> constraintsSetC = new ArrayList<>();
		File mznFile = new File ("testFiles\\testConflictDetection2.mzn");
		
		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);		
					
		BooleanExpression expression3 = new RelationalOperation<>(x2, RelationalOperator.EQ, x1);
		Constraint c3 = new Constraint("group", "c3 {x2 = x1}", expression3);
		constraintsSetC.add(c3);
		
		BooleanExpression expression4 = new RelationalOperation<>(x3, RelationalOperator.EQ, x2);
		Constraint c4 = new Constraint("group", "c4 {x3 = x2}", expression4);
		constraintsSetC.add(c4);
		
		BooleanExpression expression5 = new RelationalOperation<>(x3, RelationalOperator.GT, new IntegerConstant(2));
		Constraint c5 = new Constraint("group", "c5 {x3 > 2}", expression5);
		constraintsSetC.add(c5);
		
		boolean isConsistent = checker.isConsistent(constraintsSetC, mznFile);
		assertTrue(isConsistent);
	}
	
	@Test
	public void testConsistencyChecker_7() throws Exception{
		ConsistencyChecker checker = new ConsistencyChecker();
		List<Constraint> constraintsSetC = new ArrayList<>();
		File mznFile = new File ("testFiles\\testConflictDetection7.mzn");
		
		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);		
				
		BooleanExpression expression4 = new RelationalOperation<>(x2, RelationalOperator.EQ, x1);
		Constraint c4 = new Constraint("group", "c4 {x2 = x1}", expression4);
		constraintsSetC.add(c4);
		
		BooleanExpression expression5 = new RelationalOperation<>(x3, RelationalOperator.EQ, x2);
		Constraint c5 = new Constraint("group", "c5 {x3 = x2}", expression5);
		constraintsSetC.add(c5);
		
		BooleanExpression expression6 = new RelationalOperation<>(x3, RelationalOperator.GT, x2);
		Constraint c6 = new Constraint("group", "c6 {x3 > 2}", expression6);
		constraintsSetC.add(c6);
		
		boolean isConsistent = checker.isConsistent(constraintsSetC, mznFile);
		assertTrue(isConsistent == false);
	}
	
	@Test
	public void testQuickXPlainMinCS_2() throws Exception{
		List<Constraint> minCS = null;
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>(); 
			String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_2:", minCS);
			assertNotNull(minCS);
			
			// the expected out is (c2,c3,c4,c5)
			assertTrue(minCS.size() == 4);
			assertTrue(minCS.contains(constraintsSetC.get(1)));
			assertTrue(minCS.contains(constraintsSetC.get(2)));
			assertTrue(minCS.contains(constraintsSetC.get(3)));
			assertTrue(minCS.contains(constraintsSetC.get(4)));
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Test
	public void testQuickXPlainMinCS_NoConflict() throws Exception{
		List<Constraint> minCS = null;
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>(); 
			String fileName = UtilsForTest.getTestDataset2NoConflict(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNull(minCS);			
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_Subset2:", minCS);
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Test
	public void testQuickXPlainMinCS_5() throws Exception{
		List<Constraint> minCS = null;
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset5(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_5:", minCS);
			
			// the expected output is c2
			assertTrue(minCS.size() == 1);
			assertTrue(minCS.contains(constraintsSetC.get(1)));
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}		
	}
	
	@Test
	public void testQuickXPlainMinCS_6() throws Exception{
		List<Constraint> minCS = null;
		try{									
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_6:", minCS);			
			assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(constraintsSetC.get(0)));
			assertTrue(minCS.contains(constraintsSetC.get(1)));
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Test
	public void testQuickXPlainMinCS_7() throws Exception{
		List<Constraint> minCS = null;
		try{
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset7(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_7:", minCS);		
			
			//the expected output is c5, c6
			assertTrue(minCS.size() == 2);
			assertTrue(minCS.contains(constraintsSetC.get(4)));
			assertTrue(minCS.contains(constraintsSetC.get(5)));
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}		
	}
	
	@Test
	public void testQuickXPlainMinCS_8() throws Exception{
		List<Constraint> minCS = null;
		try{									
			List<Constraint> constraintsSetC = new ArrayList<Constraint>();
			List<Element> decisionsVar = new ArrayList<Element>();
			String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
			AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);
															
			minCS = conflictDetection.getMinConflictSet(constraintsSetC);
			assertNotNull(minCS);
			
			// expected output is c4
			DebugUtils.printConstraintsSet("testQuickXPlainMinCS_8:", minCS);			
			assertTrue(minCS.size() == 1);
			assertTrue(minCS.contains(constraintsSetC.get(3)));			
		} catch (Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
}
