package at.siemens.ct.jmz.executor;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MiniZincExecutable implements Executable {

  private static final String EXE_NAME = "minizinc";

  private final File mznFile;

  public MiniZincExecutable(File mznFile) {
    this.mznFile = mznFile;
  }

  @Override
  public String getName() {
    return EXE_NAME;
  }

  @Override
  public List<String> getOptions(Long timeoutMs, Collection<Path> searchDirectories) {
    List<String> options = new ArrayList<>();
    for (Path dir : searchDirectories) {
      options.add("-I");
      options.add(dir.toAbsolutePath().toString());
    }

    options.add(mznFile.getAbsolutePath());

    return options;
  }

}
