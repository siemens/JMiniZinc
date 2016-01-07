package at.siemens.ct.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

  /**
   * Converts an integer array to a {@link List} of {@link Integer}s.
   */
  public static List<Integer> fromArray(int[] array) {
    List<Integer> list = new ArrayList<>(array.length);
    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
    }
    assert list.size() == array.length;
    return list;
  }

  /**
   * Converts a series of elements to a {@link List} of {@link Integer}s.
   */
  @SafeVarargs
  public static <T> List<T> fromElements(T... elements) {
    List<T> list = new ArrayList<>(elements.length);
    for (int i = 0; i < elements.length; i++) {
      list.add(elements[i]);
    }
    assert list.size() == elements.length;
    return list;
  }

}
