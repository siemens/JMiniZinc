package at.siemens.ct.jmz.elements.include;

import at.siemens.ct.jmz.elements.Element;

public class IncludeItem implements Element {

  public static final String FILENAME_SUFFIX = ".mzn";

  private String fileName;

  /**
   * Includes the file with the given name. The suffix ".mzn" ({@link #FILENAME_SUFFIX}) will be appended automatically!
   * 
   * @param fileName
   *          the name of the file (without suffix .mzn)
   */
  public IncludeItem(String fileName) {
    super();
    this.fileName = fileName;
  }

  @Override
  public String declare() {
    return String.format("include \"%s%s\";", fileName, FILENAME_SUFFIX);
  }

}
