package at.siemens.ct.jmz.conflictDetection.ui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JOptionPane;

import at.siemens.ct.jmz.conflictDetection.ConflictDetectionAlgorithm;
import at.siemens.ct.jmz.conflictDetection.HSDAG.DiagnoseMetadata;
import at.siemens.ct.jmz.conflictDetection.HSDAG.DiagnoseProgressCallback;
import at.siemens.ct.jmz.conflictDetection.HSDAG.DiagnosesCollection;
import at.siemens.ct.jmz.conflictDetection.HSDAG.HSDAG;
import at.siemens.ct.jmz.conflictDetection.fastDiag.FastDiag;
import at.siemens.ct.jmz.conflictDetection.mznParser.MiniZincCP;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

public class VariableDialog implements DiagnoseProgressCallback {

	private Frame mainFrame;
	private Panel controlPanel;
	private MiniZincCP mznCp;
	private List<DecisionVariableGUI> mapWithControls;
	private ArrayList<Element> decisionVariables;
	private File mznFile;
	private TextArea textLog = new TextArea();
	private List<Constraint> userConstraints;
	private Button generateSolution;
	private final String SCD_HSDAG = "Simple Conflict Detection - HSDAG";
	private final String QUICKXPLAIN_HSDAG = "QuickXPlain - HSDAG";
	private final String FAST_DIAG = "FastDiag";
	private Choice algorithmType;

	private final String UNDEFINED = "Undefined";

	public VariableDialog(File mznFile) throws IOException, IllegalArgumentException, IllegalAccessException {
		this.mznFile = mznFile;
		mznCp = new MiniZincCP(mznFile);
		decisionVariables = (ArrayList<Element>) mznCp.getModelBuilder().elements().filter(e -> e.isVariable() == true)
				.collect(Collectors.toList());
		mapWithControls = new ArrayList<DecisionVariableGUI>();

		prepareGUI();
		addDecisionVariables();
		addComponents();

		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

		try {

			final String PROPERTY_FILENAME = "filename";
			String mznFileName = System.getProperty(PROPERTY_FILENAME, "").toString();
			File mznFile = new File(mznFileName);
			if (!mznFile.exists()) {
				JOptionPane.showMessageDialog(null, String.format("The file \"%s\" does not exist!", mznFileName),
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			/* VariableDialog variableDialog = */ new VariableDialog(mznFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void prepareGUI() {

		final String FRAME_TITLE = "Minimal diagnoses";
		mainFrame = new Frame(FRAME_TITLE);
		mainFrame.setLayout(new FlowLayout());
		// mainFrame.setLocationRelativeTo(null);

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(controlPanel);
		// mainFrame.setVisible(true);
		// mainFrame.setResizable(false);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainFrame.dispose();
			}
		});

	}

	private void addComponents() {
		Panel panel = new Panel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		Box algorithmChosing = Box.createHorizontalBox();
		algorithmChosing.setAlignmentX(Box.CENTER_ALIGNMENT);
		Label choseTheAlgorithm = new Label("Chose the algorithm:");
		algorithmChosing.add(choseTheAlgorithm);

		algorithmType = new Choice();
		algorithmType.add(SCD_HSDAG);
		//algorithmType.add(QUICKXPLAIN_HSDAG);
		algorithmType.add(FAST_DIAG);

		algorithmChosing.add(algorithmType);
		algorithmChosing.add(Box.createHorizontalGlue());

		generateSolution = new Button("Start diagnosis ...");
		algorithmChosing.add(generateSolution);
		algorithmChosing.setAlignmentX(Box.RIGHT_ALIGNMENT);
		panel.add(algorithmChosing);

		generateSolution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generateSolution.setEnabled(false);
				generateSolution();
				generateSolution.setEnabled(true);

			}

		});
		textLog.setEditable(false);
		textLog.setPreferredSize(new Dimension(500, 700));
		// textLog.

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(textLog);

		controlPanel.add(panel);
		// mainFrame.setVisible(true);
	}

	private <T> void addDecisionVariables() {

		Panel dvPanel = new Panel();
		dvPanel.setMaximumSize(new Dimension(300, 500));

		GroupLayout dvlayout = new GroupLayout(dvPanel);
		dvlayout.setAutoCreateGaps(true);
		dvlayout.setAutoCreateContainerGaps(true);

		ParallelGroup groupForElementsInLine = dvlayout.createParallelGroup(Alignment.LEADING);
		SequentialGroup groupForElementsInColumns = dvlayout.createSequentialGroup();

		Component textField = null;
		Label label;

		decisionVariables = (ArrayList<Element>) mznCp.getModelBuilder().elements().filter(e -> e.isVariable() == true)
				.collect(Collectors.toList());

		for (Element decisionVariable : decisionVariables) {
			Variable<?, ?> var = null;
			if (decisionVariable instanceof Variable<?, ?>) {
				var = (Variable<?, ?>) decisionVariable;
				if (var instanceof IntegerVariable) {
					if (var.getType() instanceof RangeExpression) {

						List<String> possibleValues;
						RangeExpression variableRange = (RangeExpression) var.getType();
						possibleValues = generateListFromRangeExpression(variableRange.getLb(), variableRange.getUb());
						Choice variableValue = new Choice();
						variableValue.add(UNDEFINED);
						if (!possibleValues.isEmpty()) {
							for (String string : possibleValues) {
								variableValue.add(string);
							}
						}
						textField = variableValue;
					} else {
						textField = new TextField();
						textField.setPreferredSize(new Dimension(87, 20));
					}

				} else if (var instanceof BooleanVariable) {

					Choice variableValue = new Choice();
					variableValue.add(UNDEFINED);
					variableValue.add("true");
					variableValue.add("false");
					textField = variableValue;
				}
			}

			label = new Label(var.getName());
			label.setMaximumSize(new Dimension(70, 20));

			groupForElementsInLine
					.addGroup((dvlayout.createSequentialGroup().addComponent(label).addComponent(textField)));
			groupForElementsInColumns.addGroup(
					(dvlayout.createParallelGroup(Alignment.CENTER).addComponent(label).addComponent(textField)));

			DecisionVariableGUI dvGUI = new DecisionVariableGUI(label, textField);
			mapWithControls.add(dvGUI);
		}

		dvlayout.setHorizontalGroup(groupForElementsInLine);
		dvlayout.setVerticalGroup(groupForElementsInColumns);
		dvPanel.setLayout(dvlayout);
		dvPanel.setBackground(Color.lightGray);
		Collections.sort(mapWithControls);
		controlPanel.add(dvPanel);
	}

	private List<String> generateListFromRangeExpression(IntegerExpression lb, IntegerExpression ub) {
		List<String> returnList = new ArrayList<String>();
		if ((lb instanceof IntegerConstant) && (ub instanceof IntegerConstant)) {
			IntegerConstant min = (IntegerConstant) lb;
			IntegerConstant max = (IntegerConstant) ub;
			for (Integer i = min.getValue(); i <= max.getValue(); i++) {
				returnList.add(i.toString());
			}
		}
		return returnList;
	}

	private List<Constraint> getAllValuesFromTheInterface() {

		ArrayList<Constraint> userConstraints = new ArrayList<Constraint>();
		String text = null;
		for (DecisionVariableGUI dvGui : mapWithControls) {
			if (dvGui.getComponent() instanceof Choice) {
				Choice choice = (Choice) dvGui.getComponent();
				text = choice.getSelectedItem();
			} else {
				TextField textField = (TextField) dvGui.getComponent();
				text = textField.getText();
			}
			if (!text.equals(UNDEFINED) && !text.isEmpty()) {
				Constraint c = createContraint(dvGui.getLabel().getText(), text);
				if (c != null) {
					userConstraints.add(c);
				}
			}

		}
		return userConstraints;

	}

	private Constraint createContraint(String variableName, String value) {
		TypeInst<?, ?> variable = mznCp.getModelBuilder().getElementByName(variableName);
		BooleanExpression expression;

		if (variable instanceof IntegerVariable) {
			IntegerVariable integervariable = (IntegerVariable) variable;
			int variableValue = integervariable.parseValue(value);
			expression = new RelationalOperation<>(integervariable, RelationalOperator.EQ,
					new IntegerConstant(variableValue));
		} else {
			BooleanVariable booleanVariable = (BooleanVariable) variable;
			boolean variableValue = booleanVariable.parseValue(value);
			expression = new RelationalOperation<>(booleanVariable, RelationalOperator.EQ,
					new BooleanConstant(variableValue));
		}

		Constraint constraint = new Constraint("userDefined", String.format("%s = %s", variableName, value),
				expression);
		return constraint;

	}

	private void generateSolution() {
		textLog.setText(null);
		userConstraints = getAllValuesFromTheInterface();
		if (userConstraints.isEmpty()) {
			JOptionPane.showMessageDialog(controlPanel, "You must set some values for decision variables!",
					"Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		try {
			String selectedAlgorithm = algorithmType.getSelectedItem();
			HSDAG hsdag;
			switch (selectedAlgorithm) {
			case SCD_HSDAG:
				hsdag = new HSDAG(mznFile.getAbsolutePath(), userConstraints, this,
						ConflictDetectionAlgorithm.SimpleConflictDetection);
				displayStartMessage();
				hsdag.diagnose();
				break;
			case QUICKXPLAIN_HSDAG:
				hsdag = new HSDAG(mznFile.getAbsolutePath(), userConstraints, this,
						ConflictDetectionAlgorithm.QuickXPlain);
				displayStartMessage();
				hsdag.diagnose();
				break;
			case FAST_DIAG:
				FastDiag fastDiag = new FastDiag(mznFile.getAbsolutePath(), userConstraints, this);
				displayStartMessage();
				fastDiag.diagnose();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(controlPanel, String.format("An error occured! %s", ex.getMessage()), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void diagnoseFound(List<Constraint> diagnose) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("DIAGNOSIS: ");
		stringBuilder.append(displayConstraintList(diagnose));
		textLog.append(stringBuilder.toString());
	}

	@Override
	public void minConflictSet(List<Constraint> minConflictSet, List<Constraint> inputConflictSet) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Check: ").append(displayConstraintList(inputConflictSet));
		if (minConflictSet == null) {
			stringBuilder.append("No minimal conflict set.").append(System.lineSeparator());
		} else {
			stringBuilder.append("Minimal conflict set: ");
			stringBuilder.append(displayConstraintList(minConflictSet));
		}
		textLog.append(stringBuilder.toString());
	}

	@Override
	public void constraintSelected(Constraint constraint) {
		String textToDIsplay = String.format("Selected constraint: { %s } %s", constraint.getConstraintName(),
				System.lineSeparator());
		textLog.append(textToDIsplay);

	}

	@Override
	public void displayMessage(String message) {
		textLog.append(message + System.lineSeparator());
	}

	private void displayStartMessage() {
		writeOutput("********************************************************");
		printFile(mznFile.getAbsolutePath());
		// writeOutput("********************************************************");
		writeOutput("");
		textLog.append("User constraints: " + displayConstraintList(userConstraints));
		writeOutput("********************************************************");
		// writeOutput("Steps");
		// writeOutput("********************************************************");
	}

	public void writeOutput(String message) {

		textLog.append(message + System.lineSeparator());
	}

	public void printFile(String fileName) {

		writeOutput("Filename: " + fileName);
		writeOutput("");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					writeOutput("\t" + line);

				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void allDiagnoses(DiagnosesCollection diagnoseCollection) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(System.lineSeparator()).append("All diagnoses: ").append(System.lineSeparator());
		for (List<Constraint> list : diagnoseCollection) {
			sBuilder.append("\t" + displayConstraintList(list));
		}
		textLog.append(sBuilder.toString());

	}

	private String displayConstraintList(List<Constraint> constraintList) {
		if (constraintList == null) {
			return "No elements in constraints set.";
		}

		SortedMap<Integer, Constraint> sortedConstraints = new TreeMap<Integer, Constraint>();
		for (Constraint c : constraintList) {
			sortedConstraints.put(userConstraints.indexOf(c), c);
		}

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{ ");
		for (Constraint c : sortedConstraints.values()) {
			stringBuilder.append(c.getConstraintName()).append(", ");
		}

		int index = stringBuilder.lastIndexOf(",");
		if (index >= 0) {
			stringBuilder.delete(index, index + 1);
		}
		stringBuilder.append("}").append(System.lineSeparator());
		return stringBuilder.toString();
	}

	@Override
	public void ignoredDiagnose(List<Constraint> diagnose, DiagnoseMetadata diagnoseMetadata) {
		String result = "";
		switch (diagnoseMetadata) {
		case AlreadyExists:
			result = String.format("Diagnosis already exists: %s", displayConstraintList(diagnose));
			break;
		case NotMin:
			result = String.format("Diagnosis is not minimal: %s", displayConstraintList(diagnose));
			break;
		}
		textLog.append(result);
	}
}
