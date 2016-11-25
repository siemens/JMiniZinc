package at.siemens.ct.jmz.writer;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.NullSolvingStrategy;
import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.bool.IntegerExpressionInSet;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetLiteral;

/**
 * Tests {@link ModelWriter} with {@link Element}s from {@link at.siemens.ct.jmz.elements.constraints}.
 *
 * @author © Siemens AG, 2016
 */
public class TestModelWriterConstraints {

  private IModelBuilder modelBuilder = new ModelBuilder();
  private IModelWriter modelWriter = new ModelWriter(modelBuilder);

  @Before
  public void setUp() {
    modelBuilder.reset();
    modelWriter.setSolvingStrategy(new NullSolvingStrategy());
  }

  /**
   * Creates an {@link IntegerExpressionInSet} constraint for an array element, writes its declaration to a string and
   * checks the result.
   */
  @Test
  public void testCreateIntExpressionInSetConstraintToString() {
    String setRangeName = "Range";
    String arrayName = "a";
    Set<Integer> setRange = new RangeExpression(1, 3).toNamedConstant(setRangeName);
		IntegerArray arrayVar = IntegerArray.createVariable(arrayName, setRange);
    java.util.Set<Integer> allowedValues = new HashSet<>();
    allowedValues.add(1);
    allowedValues.add(3);
		IntegerExpressionInSet intExpressionInSet = new IntegerExpressionInSet(arrayVar.access(1),
				SetLiteral.fromIntegers(allowedValues));
    Constraint constraint = new Constraint("test", "intExpressionInSet", intExpressionInSet);
    modelBuilder.add(setRange, arrayVar, constraint);
    String output = modelWriter.toString();

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("set of int: Range = 1..3;");
    expectedOutput.append(System.lineSeparator());
    expectedOutput.append(
        "array[" + setRangeName + "] of var int: " + arrayName + ";");
    expectedOutput.append(System.lineSeparator());
    expectedOutput.append("constraint " + arrayName + "[1] in {1, 3};");
    Assert.assertEquals(expectedOutput.toString(), output);
  }

}
