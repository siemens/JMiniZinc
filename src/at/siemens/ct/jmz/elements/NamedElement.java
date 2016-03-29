package at.siemens.ct.jmz.elements;

public interface NamedElement extends Element {

  String getName();

  default void mustHaveName() {
    if (getName() == null) {
      throw new IllegalStateException("Nameless elements cannot be declared");
    }
  }

}
