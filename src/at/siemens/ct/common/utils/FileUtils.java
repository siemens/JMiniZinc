/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author © Siemens AG, 2016
 */
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
