/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.common.utils;

import java.util.OptionalInt;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link NumberUtils}.
 * 
 * @author © Siemens AG, 2016
 */
public class TestNumberUtils {

  @Test
  public void testEqualDoubles() {
    Assert.assertTrue(NumberUtils.equal(3.14, 3.14));
  }

  @Test
  public void testUnequalDoubles() {
    Assert.assertFalse(NumberUtils.equal(3.14, 3.15));
  }

  @Test
  public void testOptPlusDoubleBothNull() {
    Double sum = NumberUtils.Opt.plus((Double) null, (Double) null);
    Assert.assertNull(sum);
  }

  @Test
  public void testOptPlusDoubleFirstNull() {
    Double sum = NumberUtils.Opt.plus((Double) null, (Double) 3.14);
    expectSum(Double.valueOf(3.14), sum);
  }

  @Test
  public void testOptPlusDoubleSecondNull() {
    Double sum = NumberUtils.Opt.plus((Double) 3.14, (Double) null);
    expectSum(Double.valueOf(3.14), sum);
  }

  @Test
  public void testOptPlusDoubleNoNull() {
    Double sum = NumberUtils.Opt.plus((Double) 1.01, (Double) 0.99);
    expectSum(Double.valueOf(2), sum);
  }

  @Test
  public void testOptPlusFloatBothNull() {
    Float sum = NumberUtils.Opt.plus((Float) null, (Float) null);
    Assert.assertNull(sum);
  }

  @Test
  public void testOptPlusFloatFirstNull() {
    Float sum = NumberUtils.Opt.plus((Float) null, 3.14f);
    expectSum(3.14f, sum);
  }

  @Test
  public void testOptPlusFloatSecondNull() {
    Float sum = NumberUtils.Opt.plus(3.14f, (Float) null);
    expectSum(3.14, sum);
  }

  @Test
  public void testOptPlusFloatNoNull() {
    Float sum = NumberUtils.Opt.plus(1.01f, 0.99f);
    expectSum(2f, sum);
  }

  @Test
  public void testOptPlusIntegerBothNull() {
    Integer sum = NumberUtils.Opt.plus((Integer) null, (Integer) null);
    Assert.assertNull(sum);
  }

  @Test
  public void testOptPlusIntegerFirstNull() {
    Integer sum = NumberUtils.Opt.plus((Integer) null, 3);
    expectSum(3, sum);
  }

  @Test
  public void testOptPlusIntegerSecondNull() {
    Integer sum = NumberUtils.Opt.plus(3, (Integer) null);
    expectSum(3, sum);
  }

  @Test
  public void testOptPlusIntegerNoNull() {
    Integer sum = NumberUtils.Opt.plus(1, 7);
    expectSum(8, sum);
  }

  @Test
  public void testOptPlusLongBothNull() {
    Long sum = NumberUtils.Opt.plus((Long) null, (Long) null);
    Assert.assertNull(sum);
  }

  @Test
  public void testOptPlusLongFirstNull() {
    Long sum = NumberUtils.Opt.plus((Long) null, Long.MAX_VALUE);
    expectSum(Long.MAX_VALUE, sum);
  }

  @Test
  public void testOptPlusLongSecondNull() {
    Long sum = NumberUtils.Opt.plus(Long.MIN_VALUE, (Long) null);
    expectSum(Long.MIN_VALUE, sum);
  }

  @Test
  public void testOptPlusLongNoNull() {
    Long sum = NumberUtils.Opt.plus(Long.MAX_VALUE - 1, 1L);
    expectSum(Long.MAX_VALUE, sum);
  }

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

  private void expectSum(float expected, float actual) {
    expectSum((double) expected, (double) actual);
  }

  private void expectSum(double expected, double actual) {
    Assert.assertTrue("Unexpected sum: " + actual, NumberUtils.equal(expected, actual));
  }

}
