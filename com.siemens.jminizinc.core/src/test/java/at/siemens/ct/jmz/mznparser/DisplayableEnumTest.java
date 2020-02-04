/*
 * Copyright Siemens AG, 2017-2020
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.mznparser;

import at.siemens.ct.jmz.elements.constraints.Constraint;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DisplayableEnumTest {

  private DisplayableEnum defaultEnum;

  @Before
  public void setUp() {
    Set<String> set = new LinkedHashSet<>();
    Collections.addAll(set, "first", "second");
    defaultEnum = new DisplayableEnum("test_enum", set);
  }

  @Test
  public void createConstraint_setUndefined_returnsNull() {
    assertNull(defaultEnum.createConstraint(Displayable.VALUE_UNDEFINED));
  }

  @Test(expected = IllegalArgumentException.class)
  public void createConstraint_setValueNotInDomain_returnsNull() {
    assertNull(defaultEnum.createConstraint("Not in domain"));
  }

  @Test
  public void createConstraint_setFirst_returnsValidConstraint() {
    List<Constraint> constraints = defaultEnum.createConstraint("first");
    assertEquals(1, constraints.size());
    Constraint constraint = constraints.get(0);
    assertEquals("userDefined", constraint.getConstraintGroup());
    assertEquals("test_enum = first", constraint.getConstraintName());
  }

  @Test
  public void getInfo() {
    List<InfoGUI> infos = defaultEnum.getInfo();
    assertEquals(1, infos.size());
    InfoGUI info = infos.get(0);
    assertEquals("test_enum", info.getLabelCaption());
    assertEquals("test_enum", info.getVariableName());
    assertEquals("Undefined", info.getValues().get(0));
    assertEquals("first", info.getValues().get(1));
    assertEquals("second", info.getValues().get(2));
  }

  @Test
  public void getName() {
    assertEquals("test_enum", defaultEnum.getName());
  }

  @Test
  public void use() {
    assertEquals("test_enum", defaultEnum.use());
  }
}