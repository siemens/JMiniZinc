package at.siemens.ct.jmz.elements;

import org.junit.Test;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;

/**
 * Tests declarations of various {@link Element}s
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestDeclarations {

  private IModelBuilder modelBuilder = new ModelBuilder();

  @Test
  public void testArrayWithListComprehension() {
    String generator = "i in 1..10";
    String expression = "10*i";
    ListComprehension comprehension = new ListComprehension(generator, expression);
    modelBuilder.createIntArrayVar("a", new IntSet(null, 1, 10), IntSet.ALL_INTEGERS,
        comprehension);

  }

}
