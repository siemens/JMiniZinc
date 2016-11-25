package at.siemens.ct.jmz.executor;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import at.siemens.ct.common.utils.ListUtils;

/**
 * @author © Siemens AG, 2016
 */
public class MiniZincIDEExecutable implements Executable {

  private static final String EXE_NAME = "MiniZincIDE";

  private final File mznFile;

  public MiniZincIDEExecutable(File mznFile) {
    this.mznFile = mznFile;
  }

  @Override
  public String getName() {
    return EXE_NAME;
  }

  @Override
  public List<String> getOptions(Long timeoutMs, Collection<Path> searchDirectories) {
    return ListUtils.fromElements(mznFile.getAbsolutePath());
  }

}
