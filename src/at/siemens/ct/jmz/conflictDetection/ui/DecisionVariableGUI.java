package at.siemens.ct.jmz.conflictDetection.ui;

import java.awt.*;

/**
 * @author z003pczy (Mara Rosu) Class used for decision variables from the
 *         interface
 *
 */
public class DecisionVariableGUI implements Comparable<DecisionVariableGUI> {

	private static int index = 0;
	private int controlIndex;
	private Label label;
	private Component component;

	public DecisionVariableGUI(Label label, Component component) {

		controlIndex = index;
		this.label = label;
		this.component = component;
		index++;
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

}
