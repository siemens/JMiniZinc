package at.siemens.ct.jmz.expressions.comprehension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.elements.IntSet;

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
  public void testSimpleListComprehension() throws Exception {
    testSimpleComprehension(ListComprehension.class, '[', ']');
  }

  /**
   * Constructs a simple {@link SetComprehension}, calls {@link SetComprehension#toString()} and checks the result.
   */
  @Test
  public void testSimpleSetComprehension() throws Exception {
    testSimpleComprehension(SetComprehension.class, '{', '}');
  }

  private <C extends Comprehension> void testSimpleComprehension(Class<C> comprehensionClass,
      char leftBracket, char rightBracket)
      throws NoSuchMethodException, SecurityException, InstantiationException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    int lb = 1, ub = 10;
    IntSet range = new IntSet(lb, ub);
    String iteratorName = "i";
    IteratorExpression iterator = range.iterate(iteratorName);
    Generator generator = new Generator(iterator);
    Constructor<C> constructor = comprehensionClass.getConstructor(Generator.class, String.class);
    C comprehension = constructor.newInstance(generator, "2 * i");
    String expectedOutput = String.format("%c 2 * %s | %s in %d..%d %c", leftBracket, iteratorName,
        iteratorName, lb, ub, rightBracket);
    Assert.assertEquals(expectedOutput, comprehension.use());
  }

}
