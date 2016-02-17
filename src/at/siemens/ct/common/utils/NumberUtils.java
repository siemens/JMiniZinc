package at.siemens.ct.common.utils;

public class NumberUtils {

  private static final double EPSILON = 0.00000001;

  /**
   * Checks if the difference between two {@link Double}s is very small.
   * 
   * @param d1
   * @param d2
   * @return {@code true} iff the absolute difference between {@code d1} and {@code d2} is less than {@link #EPSILON}.
   */
  public static boolean equal(double d1, double d2) {
    return Math.abs(d1 - d2) < EPSILON;
  }

}
