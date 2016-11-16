package at.siemens.ct.jmz.conflictDetection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

public class UtilsForTest {
	
	public static String getTestDataset2(List<Constraint> constraintsSetC, List<Element> decisionsVariable) throws FileNotFoundException{		
		File f = new File ("testFiles\\testConflictDetection2.mzn");
		
		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);
		decisionsVariable.add(setOneTwoThree);
		decisionsVariable.add(x1);
		decisionsVariable.add(x2);
		decisionsVariable.add(x3);
		
		BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(1));						
		Constraint c1 = new Constraint("group", "c1 {x1 = 1}", expression1);
		constraintsSetC.add(c1);
		
		BooleanExpression expression2 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(2));
		Constraint c2 = new Constraint("group", "c2 {x1 = 2}", expression2);
		constraintsSetC.add(c2);
		
		BooleanExpression expression3 = new RelationalOperation<>(x2, RelationalOperator.EQ, x1);
		Constraint c3 = new Constraint("group", "c3 {x2 = x1}", expression3);
		constraintsSetC.add(c3);
		
		BooleanExpression expression4 = new RelationalOperation<>(x3, RelationalOperator.EQ, x2);
		Constraint c4 = new Constraint("group", "c4 {x3 = x2}", expression4);
		constraintsSetC.add(c4);
		
		BooleanExpression expression5 = new RelationalOperation<>(x3, RelationalOperator.GT, new IntegerConstant(2));
		Constraint c5 = new Constraint("group", "c5 {x3 > 2}", expression5);
		constraintsSetC.add(c5);
		
		return f.getAbsolutePath(); 
	}
	
	public static String getTestDataset2NoConflict(List<Constraint> constraintsSetC, List<Element> decisionsVariable) throws FileNotFoundException{		
		File f = new File ("testFiles\\testConflictDetection2.mzn");
		
		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);
		decisionsVariable.add(setOneTwoThree);
		decisionsVariable.add(x1);
		decisionsVariable.add(x2);
		decisionsVariable.add(x3);
		
		/*BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(1));						
		Constraint c1 = new Constraint("group", "c1 {x1 = 1}", expression1);
		constraintsSetC.add(c1);
		
		BooleanExpression expression2 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(2));
		Constraint c2 = new Constraint("group", "c2 {x1 = 2}", expression2);
		constraintsSetC.add(c2);*/
		
		BooleanExpression expression3 = new RelationalOperation<>(x2, RelationalOperator.EQ, x1);
		Constraint c3 = new Constraint("group", "c3 {x2 = x1}", expression3);
		constraintsSetC.add(c3);
		
		BooleanExpression expression4 = new RelationalOperation<>(x3, RelationalOperator.EQ, x2);
		Constraint c4 = new Constraint("group", "c4 {x3 = x2}", expression4);
		constraintsSetC.add(c4);
		
		BooleanExpression expression5 = new RelationalOperation<>(x3, RelationalOperator.GT, new IntegerConstant(2));
		Constraint c5 = new Constraint("group", "c5 {x3 > 2}", expression5);
		constraintsSetC.add(c5);
		
		return f.getAbsolutePath(); 
	}
	
	public static String getTestDataset5(List<Constraint> constraintsSetC, List<Element> decisionsVar) throws FileNotFoundException{		
		File f = new File ("testFiles\\testConflictDetection5.mzn");
		
		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);		
		decisionsVar.add(setOneTwoThree);
		decisionsVar.add(x1);
		decisionsVar.add(x2);
		decisionsVar.add(x3);
		
		BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(1));						
		Constraint c1 = new Constraint("group", "c1 {x1 = 1}", expression1);
		constraintsSetC.add(c1);
		
		BooleanExpression expression2 = new RelationalOperation<>(x2, RelationalOperator.EQ, new IntegerConstant(2));
		Constraint c2 = new Constraint("group", "c2 {x2 = 2}", expression2);
		constraintsSetC.add(c2);
		
		BooleanExpression expression3 = new RelationalOperation<>(x3, RelationalOperator.EQ, new IntegerConstant(3));
		Constraint c3 = new Constraint("group", "c3 {x3 = 3}", expression3);
		constraintsSetC.add(c3);
		
		return f.getAbsolutePath(); 
	}
	
	public static String getTestDataset6(List<Constraint> constraintsSetC, List<Element> decisionsVar) throws FileNotFoundException{		
		File f = new File ("testFiles\\testConflictDetection6.mzn");
		
		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);		
		BooleanVariable c1 = new BooleanVariable("c1");
		BooleanVariable c2 = new BooleanVariable("c2");
		BooleanVariable c3 = new BooleanVariable("c3");
			
		decisionsVar.add(setOneTwoThree);
		decisionsVar.add(x1);
		decisionsVar.add(x2);
		decisionsVar.add(x3);
		decisionsVar.add(c1);
		decisionsVar.add(c2);
		decisionsVar.add(c3);
		
		BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(1));						
		Constraint constraint1 = new Constraint("group", "constraint1 {x1 = 1}", expression1);
		constraintsSetC.add(constraint1);
		
		BooleanExpression expression2 = new RelationalOperation<>(c1, RelationalOperator.EQ, new BooleanConstant(true));
		Constraint constraint2 = new Constraint("group", "constraint2 {c1 = true}", expression2);
		constraintsSetC.add(constraint2);
		
		return f.getAbsolutePath(); 
	}
	
	public static String getTestDataset7(List<Constraint> constraintsSetC, List<Element> decisionsVar) throws FileNotFoundException{		
		File f = new File ("testFiles\\testConflictDetection7.mzn");
		
		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);		
		decisionsVar.add(setOneTwoThree);
		decisionsVar.add(x1);
		decisionsVar.add(x2);
		decisionsVar.add(x3);
		
		BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(1));						
		Constraint c1 = new Constraint("group", "c1 {x1 = 1}", expression1);
		constraintsSetC.add(c1);
		
		BooleanExpression expression2 = new RelationalOperation<>(x2, RelationalOperator.EQ, new IntegerConstant(2));
		Constraint c2 = new Constraint("group", "c2 {x2 = 2}", expression2);
		constraintsSetC.add(c2);
					
		BooleanExpression expression3 = new RelationalOperation<>(x3, RelationalOperator.EQ, new IntegerConstant(3));
		Constraint c3 = new Constraint("group", "c3 {x3 = 3}", expression3);
		constraintsSetC.add(c3);
		
		//-----------------------------------------
		
		BooleanExpression expression4 = new RelationalOperation<>(x2, RelationalOperator.EQ, x1);
		Constraint c4 = new Constraint("group", "c4 {x2 = x1}", expression4);
		constraintsSetC.add(c4);
		
		BooleanExpression expression5 = new RelationalOperation<>(x3, RelationalOperator.EQ, x2);
		Constraint c5 = new Constraint("group", "c5 {x3 = x2}", expression5);
		constraintsSetC.add(c5);
		
		BooleanExpression expression6 = new RelationalOperation<>(x3, RelationalOperator.GT, x2);
		Constraint c6 = new Constraint("group", "c6 {x3 > x2}", expression6);
		constraintsSetC.add(c6);
		
		return f.getAbsolutePath();
	}
	
	public static String getTestDataset8(List<Constraint> constraintsSetC, List<Element> decisionsVar) throws FileNotFoundException{		
		File f = new File ("testFiles\\testConflictDetection8.mzn");
		
		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);	
		IntegerVariable x3 = new IntegerVariable("x3");
		System.out.println("x3 declaration: " + x3.declare());
		BooleanVariable c = new BooleanVariable("c");
			
		decisionsVar.add(setOneTwoThree);
		decisionsVar.add(x1);
		decisionsVar.add(x2);
		decisionsVar.add(x3);		
		decisionsVar.add(c);
		/*BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(3));						
		Constraint constraint1 = new Constraint("group", "c1 {x1 = 3}", expression1);
		constraintsSetC.add(constraint1);
		
		BooleanExpression expression2 = new RelationalOperation<>(x2, RelationalOperator.EQ, new IntegerConstant(3));						
		Constraint constraint2 = new Constraint("group", "c2 {x2 = 3}", expression2);
		constraintsSetC.add(constraint2);
		
		BooleanExpression expression3 = new RelationalOperation<>(x3, RelationalOperator.EQ, new IntegerConstant(4));						
		Constraint constraint3 = new Constraint("group", "c3 {x3 = 4}", expression3);
		constraintsSetC.add(constraint3);
				
		BooleanExpression expression4 = new RelationalOperation<>(c, RelationalOperator.EQ, new BooleanConstant(true));
		Constraint constraint4 = new Constraint("group", "c4 {c = true}", expression4);
		constraintsSetC.add(constraint4);*/
		
		BooleanExpression expression3 = new RelationalOperation<>(x3, RelationalOperator.EQ, new IntegerConstant(4));						
		Constraint constraint3 = new Constraint("group", "c1 {x3 = 4}", expression3);
		constraintsSetC.add(constraint3);
				
		BooleanExpression expression4 = new RelationalOperation<>(c, RelationalOperator.EQ, new BooleanConstant(true));
		Constraint constraint4 = new Constraint("group", "c2 {c = true}", expression4);
		constraintsSetC.add(constraint4);
		
		BooleanExpression expression1 = new RelationalOperation<>(x1, RelationalOperator.EQ, new IntegerConstant(3));						
		Constraint constraint1 = new Constraint("group", "c3 {x1 = 3}", expression1);
		constraintsSetC.add(constraint1);
		
		BooleanExpression expression2 = new RelationalOperation<>(x2, RelationalOperator.EQ, new IntegerConstant(3));						
		Constraint constraint2 = new Constraint("group", "c4 {x2 = 3}", expression2);
		constraintsSetC.add(constraint2);
		
		
				
		
		
		return f.getAbsolutePath(); 
	}
}
