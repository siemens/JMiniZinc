package at.siemens.ct.common.utils;

import java.util.OptionalInt;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link NumberUtils}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestNumberUtils {

  @Test
  public void testOptMax() {
    OptionalInt max = NumberUtils.Opt.max(OptionalInt.empty(), OptionalInt.of(-5),
        OptionalInt.of(-3), OptionalInt.of(-1), OptionalInt.of(0), OptionalInt.empty(),
        OptionalInt.of(1), OptionalInt.of(1), OptionalInt.empty());
    Assert.assertEquals(OptionalInt.of(1), max);
  }

  @Test
  public void testOptMin() {
    OptionalInt max = NumberUtils.Opt.min(OptionalInt.empty(), OptionalInt.of(-5),
        OptionalInt.of(-3), OptionalInt.of(-1), OptionalInt.of(0), OptionalInt.empty(),
        OptionalInt.of(1), OptionalInt.of(1), OptionalInt.empty());
    Assert.assertEquals(OptionalInt.of(-5), max);
  }

}
