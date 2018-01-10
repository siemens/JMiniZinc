/**
 * Copyright Siemens AG, 2016, 2018
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.executor.TestExecutor;

/**
 * Tests regular expressions that are used to parse {@link Variable}'s values in a result.
 * TODO: move to {@link at.siemens.ct.jmz.elements} package so we can access the "real" patterns
 * 
 * See also {@link TestExecutor}, where the result parser is tested implicitly.
 * 
 * @author Copyright Siemens AG, 2016
 */
public class TestRegularExpressions {

  @Test
  public void testMatchInteger() {
    Pattern pattern = Pattern.compile("\\w+ = \\d+;");
    String input = "totalCO2 = 1092654;";
    Matcher matcher = pattern.matcher(input);
        Assert.assertTrue(matcher.find());
  }

  @Test
  public void testExtractInteger() {
    Pattern pattern = Pattern.compile("\\w+ = (\\d+);");
    String input = "totalCO2 = 1092654;";
    Matcher matcher = pattern.matcher(input);

        boolean match = matcher.find();
    Assert.assertTrue("No match in pattern", match);
    String foundValue = matcher.group(1);
    Assert.assertEquals("Unexpected matching value", "1092654", foundValue);
  }

  @Test
  public void testMatchIntegerArray() {
    Pattern pattern = Pattern.compile("\\w+ = \\[(\\d+, )*\\d+\\];");
    String input = "productionStepsService = [98, 116, 102];";
    Matcher matcher = pattern.matcher(input);
        Assert.assertTrue(matcher.find());
  }

  @Test
  public void testExtractIntegerArray() {
    Pattern pattern = Pattern.compile("\\w+ = \\[((\\d+, )*(\\d+))\\];");
    String input = "productionStepsService = [98, 116, 102];";
    Matcher matcher = pattern.matcher(input);
    Assert.assertEquals("Unexpected number of groups in regex", 3, matcher.groupCount());

        boolean match = matcher.find();
    Assert.assertTrue("No match in pattern", match);
    String foundValue = matcher.group(1);
    Assert.assertEquals("Unexpected matching value", "98, 116, 102", foundValue);
  }

  @Test
  public void testMatchBoolean() {
    Pattern pattern = Pattern.compile("\\w+ = (true|false);");
    String input = "x = true;";
    Matcher matcher = pattern.matcher(input);
        Assert.assertTrue(matcher.find());
  }

  @Test
  public void testExtractBoolean() {
    Pattern pattern = Pattern.compile("\\w+ = (true|false);");
    String input = "x = true;";
    Matcher matcher = pattern.matcher(input);

        boolean match = matcher.find();
    Assert.assertTrue("No match in pattern", match);
    String foundValue = matcher.group(1);
    Assert.assertEquals("Unexpected matching value", "true", foundValue);
  }

}
