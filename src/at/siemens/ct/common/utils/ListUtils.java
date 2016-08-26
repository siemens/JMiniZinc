package at.siemens.ct.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListUtils {

  /**
   * Converts an integer array to a {@link List} of {@link Integer}s.
   */
  public static List<Integer> fromArray(int[] array) {
    List<Integer> list = new ArrayList<>(array.length);
    for (int element : array) {
      list.add(element);
    }
    assert list.size() == array.length;
    return list;
  }

  /**
	 * Flattens a two-dimensional integer array and converts it to a {@link List} of {@link Integer}s.
	 */
	public static List<Integer> fromArray(int[][] array) {
		List<Integer> list = new ArrayList<>(array.length);
		for (int[] element : array) {
			for (int i : element) {
				list.add(i);
			}
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
    for (T element : elements) {
      list.add(element);
    }
    assert list.size() == elements.length;
    return list;
  }

  /**
   * Joins all elements of the given list of lists into a one-dimensional list.
   *
   * @see ArrayUtils#toOneDimensionalList(Object[][])
   */
  public static <T> List<T> toOneDimensionalList(Collection<? extends Collection<T>> listOfLists) {
    int width = getWidth(listOfLists);
    int area = listOfLists.size() * width;
    List<T> result = new ArrayList<>(area);
    for (Collection<T> list : listOfLists) {
      int i = 0;
      for (T element : list) {
        result.add(element);
        i++;
      }
      for (; i < width; i++) {
        result.add(null);
      }
    }

    assert result.size() == area;
    return result;
  }

  /**
   * Gets the width of the given list of lists, i.e. the size of its longest element.
   */
  private static <T> int getWidth(Collection<? extends Collection<T>> listOfLists) {
    return listOfLists.stream().mapToInt(Collection::size).max().orElse(0);
  }

}
