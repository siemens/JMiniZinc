/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Collection;

import at.siemens.ct.jmz.elements.output.OutputStatement;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;

/**
 * @author Copyright Siemens AG, 2016
 */
public interface IModelWriter {

  /**
   * Returns a {@link String} of element declarations, each in a separate line.
   */
  @Override
  String toString();

  /**
   * Writes the element declarations to a {@link File}, each in a separate line.
   * 
   * @throws IOException
   */
  void toFile(File file) throws IOException;

  /**
   * Writes the element declarations to a temporary {@link File}, each in a separate line.
   * 
   * @return the generated temporary file.
   * @throws IOException
   */
  File toTempFile() throws IOException;

  /**
   * Writes the element declarations to an {@link OutputStream}, each in a separate line.
   */
  void toOutputStream(OutputStream outputStream);

  SolvingStrategy getSolvingStrategy();

  void setSolvingStrategy(SolvingStrategy solvingStrategy);

  /**
   * @see OutputStatement
   */
  @Deprecated
  OutputStatement getOutputStatement();

  /**
   * @see OutputStatement
   */
  @Deprecated
  void setOutputStatement(OutputStatement outputStatement);

  /**
   * Returns the paths where to search for included files.
   * 
   * @return a collection of paths.
   */
  Collection<Path> getSearchDirectories();

}