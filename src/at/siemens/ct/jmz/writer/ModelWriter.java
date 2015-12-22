package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;

/**
 * Provides methods that write models built by a {@link IModelBuilder} somewhere.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ModelWriter implements IModelWriter {

  private static final String TEMP_FILE_PREFIX = "jmz";
  private static final String TEMP_FILE_SUFFIX = ".mzn";

  private IModelBuilder modelBuilder;
  private SolvingStrategy solvingStrategy;

  public ModelWriter(IModelBuilder modelBuilder) {
    super();
    this.modelBuilder = modelBuilder;
  }

  @Override
  public SolvingStrategy getSolvingStrategy() {
    return solvingStrategy;
  }

  @Override
  public void setSolvingStrategy(SolvingStrategy solvingStrategy) {
    this.solvingStrategy = solvingStrategy;
  }

  @Override
  public String toString() {
    return allElements().map(Element::declare)
        .collect(Collectors.joining(System.lineSeparator()));
  }

  @Override
  public void toFile(File file) throws IOException {
    FileOutputStream outputStream = new FileOutputStream(file);
    try {
      toOutputStream(outputStream);
    } finally {
      outputStream.close();
    }
  }

  /**
   * {@inheritDoc}<br>
   * Flushes the output stream after writing, but does not close it.
   */
  @Override
  public void toOutputStream(OutputStream outputStream) {
    PrintWriter writer = new PrintWriter(outputStream);
    allElements().forEach(element -> writer.println(element.declare()));
    writer.flush();
  }

  @Override
  public File toTempFile() throws IOException {
    File file = Files.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX).toFile();
    toFile(file);
    return file;
  }

  private Stream<Element> allElements() {
    return Stream.concat(modelBuilder.elements(), Stream.of(solvingStrategy))
        .filter(s -> s.declare() != null);
  }

}
