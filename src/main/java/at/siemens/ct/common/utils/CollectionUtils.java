/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.common.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Provides utility methods for {@link Collection}s.
 * 
 * @author © Siemens AG, 2016
 */
public class CollectionUtils {

  /**
   * Computes the intersection of {@code c1} and {@code c2}.
   * 
   * @param c1
   *          a collection
   * @param c2
   *          a collection
   * @return the set of elements contained by both {@code c1} and {@code c2}.
   */
  public static <T> Set<T> intersection(Collection<? extends T> c1, Collection<? extends T> c2) {
    Set<T> intersection = new HashSet<>(c1);
    intersection.retainAll(c2);
    return intersection;
  }

  /**
   * Computes the intersection of all collections in {@code collectionsStream}.
   * 
   * @param collectionsStream
   *          a stream of collections
   * @return the set of elements that are contained by all collections in {@code collectionsStream}.
   */
  public static <T> Collection<T> intersection(Stream<Collection<T>> collectionsStream) {
    return collectionsStream.reduce(CollectionUtils::intersection)
        .orElse(Collections.emptySet());
  }

}
