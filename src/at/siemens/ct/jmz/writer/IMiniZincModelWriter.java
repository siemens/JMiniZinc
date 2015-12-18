package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.FileNotFoundException;

public interface IMiniZincModelWriter {

  /**
   * Returns a string of element declarations, each in a separate line.
   */
  String toString();

  /**
   * Writes the element declarations to a file, each in a separate line.
   */
  void toFile(File file) throws FileNotFoundException;

}