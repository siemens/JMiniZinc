package at.siemens.ct.common.utils;

/**
 * Provides utility functions for {@link String}s.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class StringUtils {

  /**
   * Removes the last occurence of {@code separator} and everything that comes before it from {@code string}.
   * 
   * @param string
   * @param separator
   * @return the remaining part of {@code string}.
   */
  public static String removePrefix(String string, String separator) {
    int posSeparator = string.lastIndexOf(separator);
    int posRemainder;
    if (posSeparator > -1) {
      posRemainder = posSeparator + separator.length();
    } else {
      posRemainder = 0;
    }
    return string.substring(posRemainder).trim();
  }

  /**
   * Removes the last occurence of {@code separator} and everything that comes after it from {@code string}.
   * 
   * @param string
   * @param separator
   * @return the remaining part of {@code string}.
   */
  public static String removePostfix(String string, String separator) {
    int posSeparator = string.lastIndexOf(separator);
    if (posSeparator == -1) {
      posSeparator = string.length();
    }
    return string.substring(0, posSeparator);
  }

}
