package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.include.IncludeItem;
import at.siemens.ct.jmz.elements.output.OutputStatement;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;
import at.siemens.ct.jmz.files.TemporaryFiles;

/**
 * Provides methods that write models built by a {@link IModelBuilder} somewhere.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ModelWriter implements IModelWriter {

  private IModelBuilder modelBuilder;
  private Collection<IncludeItem> includeItems = new ArrayList<>(1);
  private SolvingStrategy solvingStrategy;
  private OutputStatement outputStatement;

  public ModelWriter(IModelBuilder modelBuilder) {
    super();
    this.modelBuilder = modelBuilder;
  }

  @Override
  public void addIncludeItem(IncludeItem includeItem) {
    includeItems.add(includeItem);
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
  @Deprecated
  public OutputStatement getOutputStatement() {
    return outputStatement;
  }

  @Override
  @Deprecated
  public void setOutputStatement(OutputStatement outputStatement) {
    this.outputStatement = outputStatement;
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
    File file = TemporaryFiles.createMZN();
    toFile(file);
    return file;
  }

  private Stream<Element> allElements() {
    return Stream
        .concat(includeItems.stream(),
            Stream.concat(modelBuilder.elements(), Stream.of(solvingStrategy, outputStatement)))
        .filter(s -> s != null).filter(s -> s.declare() != null);
  }

}
