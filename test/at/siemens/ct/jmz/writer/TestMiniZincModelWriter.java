package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.siemens.ct.jmz.IMiniZincModelBuilder;
import at.siemens.ct.jmz.MiniZincModelBuilder;
import at.siemens.ct.jmz.elements.IntConstant;

/**
 * Tests {@link MiniZincModelWriter}
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestMiniZincModelWriter {

  private IMiniZincModelBuilder modelBuilder = new MiniZincModelBuilder();
  private IMiniZincModelWriter modelWriter = new MiniZincModelWriter(modelBuilder);

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

}
