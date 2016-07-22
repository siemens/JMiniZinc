package at.siemens.ct.common.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Provides utility methods for {@link Collection}s.
 * 
 * @author z003ft4a (Richard Taupe)
 *
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
