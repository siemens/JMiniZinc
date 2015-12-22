package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.IntConstant;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.elements.NullSolvingStrategy;

/**
 * Tests {@link ModelWriter}
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestModelWriter {

  private IModelBuilder modelBuilder = new ModelBuilder();
  private IModelWriter modelWriter = new ModelWriter(modelBuilder,
      new NullSolvingStrategy());

  @Before
  public void setUp() {
    modelBuilder.reset();
  }

  /**
   * Creates a single constant, writes its declaration to a string and checks the result.
   */
  @Test
  public void testCreateSingleConstantToString() {
    modelBuilder.createIntConstant("i", 1);
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
    modelBuilder.createIntSet("s", 1, 99);
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
    IntConstant i = modelBuilder.createIntConstant("i", 2);
    modelBuilder.createIntSet("s", 1, i);
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
    modelBuilder.createIntVar("i", IntSet.ALL_INTEGERS);
    String output = modelWriter.toString();
    Assert.assertEquals("var int: i;", output);
  }

  /**
   * Creates a variable whose type is a set of integers, writes its declaration to a string and checks the result.
   */
  @Test
  public void testCreateRestrictedIntVarToString() {
    IntSet setOneTwoThree = modelBuilder.createIntSet("OneTwoThree", 1, 3);
    modelBuilder.createIntVar("i", setOneTwoThree);
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
    IntSet setRange = modelBuilder.createIntSet(setRangeName, 1, 3);
    List<Integer> values = Arrays.asList(1, 2, 3);
    modelBuilder.createIntArrayConstant(arrayName, setRange, IntSet.ALL_INTEGERS, values);
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
    IntSet setRange = modelBuilder.createIntSet(setRangeName, 1, 3);
    modelBuilder.createIntArrayVar(arrayName, setRange, IntSet.ALL_INTEGERS);
    String output = modelWriter.toString();

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("set of int: Range = 1..3;");
    expectedOutput.append(System.lineSeparator());
    expectedOutput.append(
        "array[" + setRangeName + "] of var int: " + arrayName + ";");
    Assert.assertEquals(expectedOutput.toString(), output);
  }

}
