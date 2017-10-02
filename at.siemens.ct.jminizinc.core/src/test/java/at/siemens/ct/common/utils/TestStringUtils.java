/**
 * Copyright Siemens AG, 2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.common.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

/**
 * Tests {@link StringUtils}.
 * 
 * @author Copyright Siemens AG, 2017
 */
public class TestStringUtils {

  @Test
  public void test1Item() {
    assertEquals("item1", StringUtils.enumerate(Arrays.asList("item1")));
  }

  @Test
  public void test2Items() {
    assertEquals("item1 and item2", StringUtils.enumerate(Arrays.asList("item1", "item2")));
  }

  @Test
  public void test3Items() {
    assertEquals("item1, item2, and item3", StringUtils.enumerate(Arrays.asList("item1", "item2", "item3")));
  }

}
