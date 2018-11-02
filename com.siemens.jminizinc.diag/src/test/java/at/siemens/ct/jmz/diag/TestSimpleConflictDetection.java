/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

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
 * @author Copyright Siemens AG, 2016
 */
public class TestSimpleConflictDetection {


	@Test
  public void testSimpleConflictDetection_1() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		File f = new File("testFiles" + File.separator + "testConflictDetection1.mzn");
		// Define some additional constraints 
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();

		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);

		BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(3));
		BooleanExpression expression2 = new RelationalOperation<>(x2, RelationalOperator.EQ, new IntegerConstant(3));

		Constraint constraint = new Constraint("group", "x1 = 3", expression1);
		constraintsSetC.add(constraint);

		constraint = new Constraint("group", "x2 = 3", expression2);
		constraintsSetC.add(constraint);

		AbstractConflictDetection conflictDetection = new SimpleConflictDetection(f.getAbsolutePath());
		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
    assertNull(minCS);
	}

	@Test
  public void testSimpleConflictDetection_2() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);
		assertTrue(minCS.size() == 2);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(0)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(1)));
	}
	
	@Test
  public void testSimpleConflictDetection_2WithMoreConstraints() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset2WithMoreConstraints(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);
		assertTrue(minCS.size() == 3);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(0)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(1)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(3)));
	}

	@Test
  public void testSimpleConflictDetection_NoConflict() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset2NoConflict(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
    assertNull(minCS);
	}

	@Test
  public void testSimpleConflictDetection_5() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset1(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);
		//TODO: add some checks
		//assertTrue(minCS.size() == 2);
		//assertTrue(minCS.contains(constraintsSetC.get(0)));
		//assertTrue(minCS.contains(constraintsSetC.get(1)));
	}

	@Test
  public void testSimpleConflictDetection_6() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);

		assertTrue(minCS.size() == 2);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(0)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(1)));
	}

	

	@Test
  public void testSimpleConflictDetection_8() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new SimpleConflictDetection(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);
	

		assertTrue(minCS.size() == 1);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(0)));
	}
}
