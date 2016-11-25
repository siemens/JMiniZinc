package at.siemens.ct.jmz.ui;

import java.awt.*;
import java.util.List;

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
	private String variableName;
	private ComponentType componentType;

	// public static final String UNDEFINED = "Undefined";
	// private static final String TRUE = "true";
	// private static final String FALSE = "false";
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

	// public static DecisionVariableGUI
	// generateControlForBooleanVariable(String variableName, String
	// textFromLabel) {
	//
	// Choice variableValue = new Choice();
	// variableValue.add(TRUE);
	// variableValue.add(FALSE);
	// variableValue.add(UNDEFINED);
	// Label label = new Label(textFromLabel);
	// label.setPreferredSize(new Dimension(70, 20));
	// return new DecisionVariableGUI(label, variableValue, variableName);
	//
	// }
	//
	// public static DecisionVariableGUI
	// generateControlForIntegerVariable(IntegerVariable integerVariable) {
	// Component text;
	// if (integerVariable.getType() instanceof RangeExpression) {
	//
	// List<String> possibleValues;
	// RangeExpression variableRange = (RangeExpression)
	// integerVariable.getType();
	// possibleValues = generateListFromRangeExpression(variableRange.getLb(),
	// variableRange.getUb());
	// Choice variableValue = new Choice();
	// variableValue.add(UNDEFINED);
	// if (!possibleValues.isEmpty()) {
	// for (String string : possibleValues) {
	// variableValue.add(string);
	// }
	// }
	// text = variableValue;
	// text.setPreferredSize(new Dimension(87, 20));
	// } else {
	// text = new TextField();
	// text.setPreferredSize(new Dimension(87, 20));
	// }
	//
	// Label label = new Label(integerVariable.getName());
	// label.setPreferredSize(new Dimension(70, 20));
	//
	// return new DecisionVariableGUI(label, text, integerVariable.getName());
	// }
	//
	// public static DecisionVariableGUI
	// generateControlForIntegerVariable(String name, RangeExpression
	// variableRange,
	// String textFromLabel) {
	// Component text;
	// if (variableRange != null) {
	//
	// List<String> possibleValues;
	// possibleValues = generateListFromRangeExpression(variableRange.getLb(),
	// variableRange.getUb());
	// Choice variableValue = new Choice();
	// variableValue.add(UNDEFINED);
	// if (!possibleValues.isEmpty()) {
	// for (String string : possibleValues) {
	// variableValue.add(string);
	// }
	// }
	// text = variableValue;
	// text.setPreferredSize(new Dimension(87, 20));
	// } else {
	// text = new TextField();
	// text.setPreferredSize(new Dimension(87, 20));
	// }
	//
	// Label label = new Label(textFromLabel);
	// label.setPreferredSize(new Dimension(70, 20));
	//
	// return new DecisionVariableGUI(label, text, name);
	// }
	//
	// private static List<String>
	// generateListFromRangeExpression(IntegerExpression lb, IntegerExpression
	// ub) {
	// List<String> returnList = new ArrayList<String>();
	// if ((lb instanceof IntegerConstant) && (ub instanceof IntegerConstant)) {
	// IntegerConstant min = (IntegerConstant) lb;
	// IntegerConstant max = (IntegerConstant) ub;
	// for (Integer i = min.getValue(); i <= max.getValue(); i++) {
	// returnList.add(i.toString());
	// }
	// }
	// return returnList;
	// }
	//
	// public static ArrayList<DecisionVariableGUI>
	// generateControlsForDecisionVariables(
	// List<TypeInst<?, ?>> decisionVariables) {
	//
	// ArrayList<DecisionVariableGUI> mapWithControls = new
	// ArrayList<DecisionVariableGUI>();
	//
	// for (TypeInst<?, ?> decisionVariable : decisionVariables) {
	//
	// DecisionVariableGUI dvGui;
	//
	// if (decisionVariable instanceof IntegerVariable) {
	//
	// IntegerVariable integerVar = (IntegerVariable) decisionVariable;
	// dvGui = generateControlForIntegerVariable(integerVar);
	// mapWithControls.add(dvGui);
	//
	// } else if (decisionVariable instanceof BooleanVariable) {
	//
	// BooleanVariable boolVar = (BooleanVariable) decisionVariable;
	// dvGui = generateControlForBooleanVariable(boolVar.getName(),
	// boolVar.getName());
	// mapWithControls.add(dvGui);
	//
	// } else if (decisionVariable instanceof BooleanArray) {
	// BooleanArray boolArray = (BooleanArray) decisionVariable;
	// RangeExpression arrayIndexRange = (RangeExpression)
	// boolArray.getRange().get(0);
	// List<String> indices;
	// String arrayName = boolArray.getName();
	// indices = generateListFromRangeExpression(arrayIndexRange.getLb(),
	// arrayIndexRange.getUb());
	// for (String string : indices) {
	//
	// String variableName = String.format("%s[%s]", arrayName, string);
	// dvGui = generateControlForBooleanVariable(arrayName, variableName);
	// mapWithControls.add(dvGui);
	// }
	// } else if (decisionVariable instanceof IntegerArray) {
	// IntegerArray integerArray = (IntegerArray) decisionVariable;
	// RangeExpression arrayIndexRange = (RangeExpression)
	// integerArray.getRange().get(0);
	// List<String> indices;
	// String arrayName = integerArray.getName();
	// indices = generateListFromRangeExpression(arrayIndexRange.getLb(),
	// arrayIndexRange.getUb());
	// for (String string : indices) {
	//
	// String variableName = String.format("%s[%s]", arrayName, string);
	// if (integerArray.getType() instanceof RangeExpression) {
	// dvGui = generateControlForIntegerVariable(arrayName, (RangeExpression)
	// integerArray.getType(),
	// variableName);
	// } else {
	// dvGui = DecisionVariableGUI.generateControlForIntegerVariable(arrayName,
	// null, variableName);
	// }
	//
	// mapWithControls.add(dvGui);
	//
	// }
	//
	// }
	//
	// }
	// return mapWithControls;
	//
	// }

}
