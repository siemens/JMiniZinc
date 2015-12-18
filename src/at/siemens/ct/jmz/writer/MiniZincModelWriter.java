package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.IMiniZincModelBuilder;
import at.siemens.ct.jmz.elements.MiniZincElement;

/**
 * Provides methods that write models built by a {@link IMiniZincModelBuilder} somewhere.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class MiniZincModelWriter implements IMiniZincModelWriter {

  private IMiniZincModelBuilder modelBuilder;

  public MiniZincModelWriter(IMiniZincModelBuilder modelBuilder) {
    super();
    this.modelBuilder = modelBuilder;
  }

  @Override
  public String toString() {
    return modelBuilder.elements().map(MiniZincElement::declare)
        .collect(Collectors.joining(System.lineSeparator()));
  }

  @Override
  public void toFile(File file) throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(file);
    try {
      modelBuilder.elements().forEach(element -> writer.println(element.declare()));
    } finally {
      writer.close();
    }
  }

}
