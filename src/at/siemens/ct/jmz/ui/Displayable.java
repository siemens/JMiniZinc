package at.siemens.ct.jmz.ui;

import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * An interface for variables that can be displayed on {@link VariableDialog}.
 * 
 * @author z003pczy
 *
 */
public interface Displayable {

	public List<Constraint> createConstraint(String values) throws Exception;

	public List<InfoGUI> getInfo();
	
	
	public String getName();



}
