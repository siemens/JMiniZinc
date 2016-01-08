package at.siemens.ct.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

  /**
   * Joins all rows of the given two-dimensional array into a one-dimensional list.
   */
  public static <T> List<T> toOneDimensionalList(T[][] array) {
    int width = getWidth(array);
    List<T> list = new ArrayList<>(array.length * width);
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < width; j++) {
        if (array[i].length > j) {
          list.add(array[i][j]);
        } else {
          list.add(null);
        }
      }
    }
    return list;
  }

  /**
   * @see #toOneDimensionalList(Object[][])
   */
  public static List<Integer> toOneDimensionalList(int[][] values) {
    return toOneDimensionalList(box(values));
  }

  /**
   * Gets the width of the given two-dimensional array, i.e. the length of its longest row.
   */
  public static <T> int getWidth(T[][] array) {
    int width = 0;
    for (int i = 0; i < array.length; i++) {
      width = Integer.max(width, array[i].length);
    }
    return width;
  }

  /**
   * Boxes each element in an int array to {@link Integer}.
   */
  public static Integer[][] box(int[][] array) {
    Integer[][] boxed = new Integer[array.length][];
    for (int i = 0; i < array.length; i++) {
      int width = array[i].length;
      boxed[i] = new Integer[width];
      for (int j = 0; j < width; j++) {
        boxed[i][j] = Integer.valueOf(array[i][j]);
      }
    }
    return boxed;
  }

}
