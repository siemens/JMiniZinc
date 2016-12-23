/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.ui;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;
import java.util.List;

import at.siemens.ct.jmz.mznparser.ComponentType;
import at.siemens.ct.jmz.mznparser.InfoGUI;

/**
 * Class used for decision variables from the interface
 * @author z003pczy (Mara Rosu)
 *
 */
public class DecisionVariableGUI implements Comparable<DecisionVariableGUI> {

	private static int index = 0;
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

		List<String> possibleValues = variableInformations.getValues();

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

		component.setPreferredSize(COMPONENT_DIMENSION);

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
