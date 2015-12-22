package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public interface IModelWriter {

  /**
   * Returns a {@link String} of element declarations, each in a separate line.
   */
  @Override
  String toString();

  /**
   * Writes the element declarations to a {@link File}, each in a separate line.
   */
  void toFile(File file) throws FileNotFoundException;

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

}