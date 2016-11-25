package at.siemens.ct.common.utils;

/**
 * Provides utility functions for {@link String}s.
 * 
 * @author © Siemens AG, 2016
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
    return removePrefix(string, posSeparator, separator.length());
  }

  /**
   * The case-insensitive version of {@link #removePrefix(String, String)}.
   */
  public static String removePrefixCaseInsensitive(String string, String separator) {
    int posSeparator = string.toLowerCase().lastIndexOf(separator.toLowerCase());
    return removePrefix(string, posSeparator, separator.length());
  }

  private static String removePrefix(String string, int posSeparator, int separatorLength) {
    int posRemainder;
    if (posSeparator > -1) {
      posRemainder = posSeparator + separatorLength;
    } else {
      posRemainder = 0;
    }
    return string.substring(posRemainder);
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
    return removePostfix(string, posSeparator);
  }

  /**
   * The case-insensitive version of {@link #removePostfix(String, String)}.
   */
  public static String removePostfixCaseInsensitive(String string, String separator) {
    int posSeparator = string.toLowerCase().lastIndexOf(separator.toLowerCase());
    return removePostfix(string, posSeparator);
  }

  private static String removePostfix(String string, int posSeparator) {
    if (posSeparator == -1) {
      posSeparator = string.length();
    }
    return string.substring(0, posSeparator);
  }

  /**
   * Removes the last occurence of {@code postfixSeparator} and everything that comes after it from {@code input},
   * then removes the last occurence of {@code prefixSeparator} and everything that comes before it from the remaining
   * part of {@code input}.
   * 
   * @param input
   * @param prefixSeparator
   * @param postfixSeparator
   * @return the remaining part of {@code input}.
   */
  public static String removePostfixAndPrefix(String input, String prefixSeparator,
      String postfixSeparator) {
    String inputFront = StringUtils.removePostfix(input, postfixSeparator);
    return removePrefix(inputFront, prefixSeparator);
  }

  /**
   * The case-insensitive version of {@link #removePostfixAndPrefix(String, String, String)}.
   */
  public static String removePostfixAndPrefixCaseInsensitive(String input, String prefixSeparator,
      String postfixSeparator) {
    String inputFront = StringUtils.removePostfixCaseInsensitive(input, postfixSeparator);
    return removePrefixCaseInsensitive(inputFront, prefixSeparator);
  }

}
