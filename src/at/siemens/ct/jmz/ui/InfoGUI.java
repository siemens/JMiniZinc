package at.siemens.ct.jmz.ui;

import java.util.List;

public class InfoGUI {

	private String labelCaption;
	private String variableName;
	private ComponentType componentType;
	private List<String> values;

	public InfoGUI(String labelCaption, String variableName, ComponentType componentType, List<String> values) {
		this.labelCaption = labelCaption;
		this.variableName = variableName;
		this.componentType = componentType;
		this.values = values;
	}

	public String getLabelCaption() {
		return labelCaption;
	}

	public String getVariableName() {
		return variableName;
	}

	public ComponentType getComponentType() {
		return componentType;
	}

	public List<String> getValues() {
		return values;
	}
	
	

}
