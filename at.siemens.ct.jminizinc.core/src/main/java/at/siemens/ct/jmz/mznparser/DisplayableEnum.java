package at.siemens.ct.jmz.mznparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public class DisplayableEnum implements Displayable  {

  private final String name;
  private Set<String> range;


  public DisplayableEnum(String name, Set<String> range) {
    this.name = name;
    this.range = range;
  }

  @Override
  public List<Constraint> createConstraint(String value) {
    if (value.equals("Undefined") || value.isEmpty())
      return null;

    if (!range.contains(value)){
      throw new IllegalArgumentException("Value not in domain: " + value);
    }

    return null;
  }

  @Override
  public List<InfoGUI> getInfo() {
    ArrayList<String> values = new ArrayList<>();
    values.add("Undefined");
    values.addAll(range);
    return Collections.singletonList(new InfoGUI(name, name, ComponentType.CHOICE, values));
  }

  @Override
  public String getName() {
    return name;
  }
}
