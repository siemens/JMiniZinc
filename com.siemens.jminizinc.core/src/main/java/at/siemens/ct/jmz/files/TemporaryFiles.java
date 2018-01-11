/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Provides methods to create temporary files.
 * 
 * @author Copyright Siemens AG, 2016
 */
public abstract class TemporaryFiles {

  private static final String TEMP_FILE_PREFIX = "jmz";
  private static final String MZN_POSTFIX = ".mzn";
  private static final String FZN_POSTFIX = ".fzn";

  public static File createMZN() throws IOException {
    return create(TEMP_FILE_PREFIX, MZN_POSTFIX);
  }

  public static File createFZN() throws IOException {
    return create(TEMP_FILE_PREFIX, FZN_POSTFIX);
  }

  private static File create(String prefix, String postfix) throws IOException {
    File file = Files.createTempFile(prefix, postfix).toFile();
    file.deleteOnExit();
    return file;
  }

}
