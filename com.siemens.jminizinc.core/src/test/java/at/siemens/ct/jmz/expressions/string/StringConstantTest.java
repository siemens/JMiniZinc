package at.siemens.ct.jmz.expressions.string;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringConstantTest {

  private StringConstant constant = new StringConstant(null, "test_value");

  @Test
  public void compareTo_sameValues_returnsZero() {
    assertEquals(0, constant.compareTo(new StringConstant(null, "test_value")));
  }

  @Test
  public void compareTo_notSameValues_returnsNotZero() {
    assertNotEquals(0, constant.compareTo(new StringConstant(null, "another_test_value")));
  }
}