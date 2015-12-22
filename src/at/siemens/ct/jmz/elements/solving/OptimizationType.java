package at.siemens.ct.jmz.elements.solving;

public enum OptimizationType {
  MIN("minimize"), MAX("maximize");

  private String keyword;

  OptimizationType(String keyword) {
    this.keyword = keyword;
  }

  @Override
  public String toString() {
    return keyword;
  }
}