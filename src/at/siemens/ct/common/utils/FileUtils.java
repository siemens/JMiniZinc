package at.siemens.ct.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {

  /**
   * Reads an entire text file into a string.
   */
  public static String readText(File file) throws IOException {
    return new String(Files.readAllBytes(file.toPath()));
  }

  /**
   * Reads all lines from a text file into a list of strings..
   */
  public static List<String> readLines(File file) throws IOException {
    return Files.readAllLines(file.toPath());
  }

}
