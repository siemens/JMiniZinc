package at.siemens.ct.jmz.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Provides methods to create temporary files.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public abstract class TemporaryFiles {

  private static final String TEMP_FILE_PREFIX = "jmz";
  private static final String MZN_POSTFIX = ".mzn";
  private static final String FZN_POSTFIX = "jmz";

  public static File createMZN() throws IOException {
    return create(TEMP_FILE_PREFIX, MZN_POSTFIX);
  }

  public static File createFZN() throws IOException {
    return create(TEMP_FILE_PREFIX, FZN_POSTFIX);
  }

  private static File create(String prefix, String postfix) throws IOException {
    return Files.createTempFile(prefix, postfix).toFile();
  }

}
