/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
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
 * @author Copyright Siemens AG, 2016
 */
public class ModelWriter implements IModelWriter {

  private IModelBuilder modelBuilder;
  private SolvingStrategy solvingStrategy;
  private OutputStatement outputStatement;

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
    try (FileOutputStream outputStream = new FileOutputStream(file)) {
      toOutputStream(outputStream);
    }
  }

  /**
   * {@inheritDoc}<br>
   * Flushes the output stream after writing, but does not close it.
   */
  @Override
  public void toOutputStream(OutputStream outputStream) {
    PrintWriter writer = new PrintWriter(outputStream);
    for (Element element : allElements().collect(Collectors.toList())) {
      writer.println(element.declare());
    }
    writer.flush();
  }

  @Override
  public File toTempFile() throws IOException {
    File file = TemporaryFiles.createMZN();
    toFile(file);
    return file;
  }

  private Stream<Element> allElements() {
    return Stream.concat(modelBuilder.elements(), Stream.of(solvingStrategy, outputStatement))
        .filter(Objects::nonNull).filter(s -> s.declare() != null);
  }

  private Stream<IncludeItem> includeItems() {
    return allElements().filter(e -> e instanceof IncludeItem).map(e -> (IncludeItem) e);
  }

  @Override
  public Collection<Path> getSearchDirectories() {
    return includeItems().map(IncludeItem::getDirectory).filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }

}
