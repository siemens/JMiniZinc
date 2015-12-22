package at.siemens.ct.jmz.expressions.comprehension;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link Comprehension} and its concrete implementations.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestComprehension {

  /**
   * Constructs a simple {@link ListComprehension}, calls {@link ListComprehension#toString()} and checks the result.
   */
  @Test
  public void testSimpleListComprehension() {
    ListComprehension comprehension = new ListComprehension("i in Integers", "2 * i");
    Assert.assertEquals("[ 2 * i | i in Integers ]", comprehension.toString());
  }

  /**
   * Constructs a simple {@link SetComprehension}, calls {@link SetComprehension#toString()} and checks the result.
   */
  @Test
  public void testSimpleSetComprehension() {
    SetComprehension comprehension = new SetComprehension("i in Integers", "2 * i");
    Assert.assertEquals("{ 2 * i | i in Integers }", comprehension.toString());
  }

}
