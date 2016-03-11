package at.siemens.ct.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

  /**
   * Reads an entire text file into a string.
   */
  public static String readText(File file) throws IOException {
    return new String(Files.readAllBytes(file.toPath()));
  }

}
