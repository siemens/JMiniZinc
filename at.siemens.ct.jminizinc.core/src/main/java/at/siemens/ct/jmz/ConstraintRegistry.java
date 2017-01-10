/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * Maintains names and group memberships of {@link Constraint}s.
 * 
 * @author © Siemens AG, 2016
 */
public class ConstraintRegistry {

  public static final String NAME_SEPARATOR = ".";

  private final Map<String, Set<String>> mapGroupToNames = new HashMap<>();
  private final Map<List<String>, Constraint> mapGroupAndNameToConstraint = new HashMap<>();
  private final Set<String> ignoredGroups = new HashSet<>();
  private final Set<List<String>> ignoredKeys = new HashSet<>();

  /**
   * Registers a constraint.
   * @param constraint
   */
  public void register(Constraint constraint) {
    String group = constraint.getConstraintGroup();
    String name = constraint.getConstraintName();
    mapGroupToName(group, name);
    mapGroupAndNameToConstraint(group, name, constraint);
  }

  private void mapGroupToName(String group, String name) {
    if (!mapGroupToNames.containsKey(group)) {
      mapGroupToNames.put(group, new HashSet<>());
    }
    mapGroupToNames.get(group).add(name);
  }

  private void mapGroupAndNameToConstraint(String group, String name, Constraint constraint) {
    List<String> key = key(group, name);
    if (mapGroupAndNameToConstraint.containsKey(key))
      throw new IllegalArgumentException(
          "Constraint already exists: " + group + NAME_SEPARATOR + name);
    
    mapGroupAndNameToConstraint.put(key, constraint);
  }

  public void ignoreGroups(String... groups) {
    for (String group : groups) {
      ignoredGroups.add(group);
    }
  }

  public void ignoreConstraint(String group, String name) {
    ignoredKeys.add(key(group, name));
  }

  public boolean isIgnored(Constraint constraint) {
    String group = constraint.getConstraintGroup();
    String name = constraint.getConstraintName();

    return ignoredGroups.contains(group) || ignoredKeys.contains(key(group, name));
  }

  private List<String> key(String group, String name) {
    return Arrays.asList(group, name);
  }

  /**
   * Builds a constraint name from a given prefix and list of {@link BasicTypeInst}s.
   * 
   * @param prefix
   * @param namedElements
   * @return the concatenation of the prefix and the names of the given elements, separated by {@link #NAME_SEPARATOR}.
   */
  public static String buildName(String prefix, TypeInst<?, ?>... namedElements) {
    StringBuilder builder = new StringBuilder(prefix);
    for (TypeInst<?, ?> namedElement : namedElements) {
      builder.append(NAME_SEPARATOR);
      builder.append(namedElement.getName());
    }
    return builder.toString();
  }

  public Collection<String> getGroups() {
    return Collections.unmodifiableCollection(mapGroupToNames.keySet());
  }

  public void reset() {
    mapGroupToNames.clear();
    mapGroupAndNameToConstraint.clear();
    ignoredGroups.clear();
    ignoredKeys.clear();
  }

}
