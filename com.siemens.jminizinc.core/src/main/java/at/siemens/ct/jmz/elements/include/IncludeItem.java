/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements.include;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import at.siemens.ct.jmz.elements.Element;

/**
 * @author Copyright Siemens AG, 2016
 */
public class IncludeItem implements Element {

  public static final String FILENAME_SUFFIX = ".mzn";

  private String fileName;
  private Path directory;

  /**
   * Includes the file with the given name. The suffix ".mzn" ({@link #FILENAME_SUFFIX}) will be appended automatically!
   * 
   * @param fileName
   *          the name of the file (without suffix .mzn)
   */
  public static IncludeItem standard(String fileName) {
	return new IncludeItem(fileName);
  }

  private IncludeItem(String fileName) {
    this(null, fileName);
  }

  /**
   * Includes the file with the given name in the given directory. The suffix ".mzn" ({@link #FILENAME_SUFFIX}) will be
   * appended automatically!
   * 
   * @param directory
   *          the path of the directory where the file is located
   * @param fileName
   *          the name of the file (without suffix .mzn)
   */
  public static IncludeItem file(Path directory, String fileName) {
	  return new IncludeItem(directory, fileName);
  }
  
  private IncludeItem(Path directory, String fileName) {
    this.directory = directory;
    this.fileName = removeSuffix(fileName);
  }
  
  /**
   * Includes the resource with the given name (which will be loaded using {@link ClassLoader#getResource(String)}).
   * @param resourceName
   * @throws URISyntaxException 
   */
  public static IncludeItem resource(String resourceName) throws URISyntaxException {
    Path workingDirectory = Paths.get(".").toAbsolutePath().getParent();
    URI uri = Thread.currentThread().getContextClassLoader().getResource(resourceName).toURI();
	return new IncludeItem(workingDirectory.relativize(Paths.get(uri)).toString().replace('\\', '/'));
  }

  public IncludeItem(File file) {
    this(file.getParentFile().toPath(), file.getName());
  }

  /**
   * Returns the path of the directory where the included file is located, if it is known.
   */
  public Path getDirectory() {
    return directory;
  }

  @Override
  public String declare() {
    return String.format("include \"%s%s\";", fileName, FILENAME_SUFFIX);
  }

  private static String removeSuffix(String fileName) {
    if (fileName.endsWith(FILENAME_SUFFIX)) {
      return fileName.substring(0, fileName.length() - FILENAME_SUFFIX.length());
    }
    return fileName;
  }

}
