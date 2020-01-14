/*
 * Copyright Siemens AG, 2016
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.ui;

import at.siemens.ct.jmz.mznparser.ComponentType;
import at.siemens.ct.jmz.mznparser.InfoGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for decision variables from the interface
 *
 * @author z003pczy (Mara Rosu)
 */
public class DecisionVariableGUI implements Comparable<DecisionVariableGUI> {

	private static int index = 0;

	private final List<String> possibleValues;
	private final List<Boolean> valueValidity;

	private int controlIndex;
	private Label label;
	private Component component;
	private String variableName;
	private ComponentType componentType;

	private final Dimension COMPONENT_DIMENSION = new Dimension(87, 20);
	private final Dimension LABEL_DIMENSION = new Dimension(70, 20);

	public DecisionVariableGUI(InfoGUI variableInformations) {

		controlIndex = index;
		index++;

		componentType = variableInformations.getComponentType();
		label = new Label(variableInformations.getLabelCaption());
		label.setPreferredSize(LABEL_DIMENSION);
		variableName = variableInformations.getVariableName();

		possibleValues = variableInformations.getValues();
		valueValidity = new ArrayList<>(possibleValues.size());
		for (int i = 0; i < possibleValues.size(); i++) {
			valueValidity.add(true);
		}

		switch (componentType) {
			case TEXTFIELD:
				component = new TextField();
				break;

			case CHOICE:
				Choice variableValues = new Choice();
				if (!possibleValues.isEmpty()) {
					for (String value : possibleValues) {
						variableValues.add(value);
					}
					component = variableValues;
				}

				break;
		}

		if (component == null) throw new RuntimeException("Invalid component type");

		component.setPreferredSize(COMPONENT_DIMENSION);

	}

	public void setValueValidity(String value, boolean valid) {
		if (componentType != ComponentType.CHOICE) {
			throw new IllegalArgumentException("Only choice decisions can have value validity");
		}

		int index = possibleValues.indexOf(value);
		if (index < 0) {
			throw new IllegalArgumentException("Value " + value + " is not a possible value for " + this.variableName);
		}

		valueValidity.set(index, valid);

		regenerateOptionNames();
	}

	private void regenerateOptionNames() {
		if (componentType == ComponentType.CHOICE) {
			Choice choiceComponent = (Choice) component;
			int selected = choiceComponent.getSelectedIndex();
			choiceComponent.removeAll();
			for (int i = 0; i < possibleValues.size(); i++) {
				String value = possibleValues.get(i);
				Boolean validity = valueValidity.get(i);

				if (validity) {
					choiceComponent.add(value);
				} else {
					choiceComponent.add(value + " (invalid)");
				}
			}
			choiceComponent.select(selected);
		}
	}


	@Override
	public int compareTo(DecisionVariableGUI o) {
		return Integer.compare(this.controlIndex, o.controlIndex);

	}

	public Label getLabel() {
		return label;
	}

	public Component getComponent() {
		return component;
	}

	public String getVariableName() {
		return variableName;
	}

	public String getVariableValue() {
		if (componentType == ComponentType.CHOICE)
			return ((Choice) this.component).getSelectedItem();
		else
			return ((TextField) this.component).getText();
	}

	@Override
	public String toString() {
		return "DecisionVariableGUI [label=" + label + ", variableName=" + variableName + "]";
	}

}
