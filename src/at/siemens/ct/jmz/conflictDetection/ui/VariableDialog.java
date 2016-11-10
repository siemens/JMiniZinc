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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JOptionPane;

import at.siemens.ct.jmz.conflictDetection.HSDAG.DiagnoseProgressCallback;
import at.siemens.ct.jmz.conflictDetection.HSDAG.HSDAG;
import at.siemens.ct.jmz.conflictDetection.mznParser.MiniZincCP;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.bool.RelationalExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

public class VariableDialog<V> implements DiagnoseProgressCallback {

	private Frame mainFrame;
	private Panel controlPanel;
	private MiniZincCP mznCp;
	private Map<Label, Component> map;
	private ArrayList<Element> decisionVariables;
	private File mznFile;
	private TextArea textLog = new TextArea();

	public VariableDialog(File mznFile) throws IOException, IllegalArgumentException, IllegalAccessException {
		this.mznFile = mznFile;
		mznCp = new MiniZincCP(mznFile);
		decisionVariables = (ArrayList<Element>) mznCp.getModelBuilder().elements().filter(e -> e.isVariable() == true)
				.collect(Collectors.toList());
		map = new HashMap<Label, Component>();

		prepareGUI();
		addDecisionVariables();
		addComponents();
		mainFrame.pack();

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

		mainFrame = new Frame("Conflict Detection");
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setLocationRelativeTo(null);
		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);

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

		Choice algorithmType = new Choice();
		algorithmType.add("Simple Conflict Detection - HSDAG");
		// algotithm.add("QuickXPlain");
		// algotithm.add("FastDiag");
		// algotithm.setEnabled(false); // todo: Temporary

		algorithmChosing.add(algorithmType);
		algorithmChosing.add(Box.createHorizontalGlue());
		
		
		Button generateSolution = new Button("Start diagnosis ...");
		algorithmChosing.add(generateSolution);
		algorithmChosing.setAlignmentX(Box.RIGHT_ALIGNMENT);
		panel.add(algorithmChosing);

		generateSolution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generateSolution();

			}

		});
		textLog.setEditable(false);
		textLog.setPreferredSize(new Dimension(500, 200));

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(textLog);

		controlPanel.add(panel);
		mainFrame.setVisible(true);
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

			Variable<T, V> var = (Variable<T, V>) decisionVariable;

			if (var instanceof IntegerVariable) {
				if (var.getType() instanceof RangeExpression) {

					List<String> possibleValues;

					RangeExpression variableRange = (RangeExpression) var.getType();

					possibleValues = generateListFromRangeExpression(variableRange.getLb(), variableRange.getUb());

					Choice variableValue = new Choice();

					variableValue.add("Undefined");

					if (!possibleValues.isEmpty()) {
						for (String string : possibleValues) {
							variableValue.add(string);
						}
					}

					textField = variableValue;
				} else {
					textField = new TextField();
					textField.setPreferredSize(new Dimension(70, 20));
				}

			} else if (var instanceof BooleanVariable) {

				Choice variableValue = new Choice();

				variableValue.add("Undefined");
				variableValue.add("true");
				variableValue.add("false");

				textField = variableValue;
			}

			label = new Label(var.getName());
			label.setMaximumSize(new Dimension(70, 20));

			groupForElementsInLine
					.addGroup((dvlayout.createSequentialGroup().addComponent(label).addComponent(textField)));
			groupForElementsInColumns.addGroup(
					(dvlayout.createParallelGroup(Alignment.CENTER).addComponent(label).addComponent(textField)));

			map.put(label, textField);
		}

		dvlayout.setHorizontalGroup(groupForElementsInLine);
		dvlayout.setVerticalGroup(groupForElementsInColumns);

		dvPanel.setLayout(dvlayout);

		dvPanel.setBackground(Color.lightGray);

		controlPanel.add(dvPanel);

		mainFrame.setVisible(true);

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
		for (Entry<Label, Component> entry : map.entrySet()) {
			if (entry.getValue() instanceof Choice) {
				Choice choice = (Choice) entry.getValue();
				text = choice.getSelectedItem();
			} else {
				TextField textField = (TextField) entry.getValue();
				text = textField.getText();
			}
			if (!text.equals("Undefined") && !text.isEmpty()) {
				Constraint c = createContraint(entry.getKey().getText(), text);
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
			expression = new RelationalExpression<>(integervariable, RelationalOperator.EQ,
					new IntegerConstant(variableValue));
		} else {
			BooleanVariable booleanVariable = (BooleanVariable) variable;
			boolean variableValue = booleanVariable.parseValue(value);
			expression = new RelationalExpression<>(booleanVariable, RelationalOperator.EQ,
					new BooleanConstant(variableValue));
		}

		Constraint constraint = new Constraint("userDefined", String.format("%s = %s", variableName, value),
				expression);
		return constraint;

	}

	private void generateSolution() {
		textLog.setText(null);
		List<Constraint> userConstraints = getAllValuesFromTheInterface();
		if (userConstraints.isEmpty()) {
			JOptionPane.showMessageDialog(controlPanel, "You must set some values for decision variables!",
					"Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		try {
			// todo: select algorithm according to user selection.
			HSDAG hsdag = new HSDAG(mznFile.getAbsolutePath(), userConstraints, null);
			textLog.setText("The solution is computing...");
			String diagnoseResult = hsdag.diagnose();
			if (diagnoseResult != null) {
				textLog.setText(diagnoseResult);
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(controlPanel, String.format("An error occured! %s", ex.getMessage()), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void diagnoseFound(List<Constraint> diagnose) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Diagnose found: D = {");
		for (Constraint constraint : diagnose) {
			stringBuilder.append(constraint.getConstraintName());
		}
		stringBuilder.append(" }/n");
		
		textLog.append(stringBuilder.toString());
	}

	@Override
	public void minConflictSetFound(List<Constraint> minConflictSet) {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Minimal Conflict Set found: CS = {");
		for (Constraint constraint : minConflictSet) {
			stringBuilder.append(constraint.getConstraintName());
		}
		stringBuilder.append(" }/n");
		textLog.append(stringBuilder.toString());
	}

	@Override
	public void constraintSelected(Constraint constraint) {
		String textToDIsplay = String.format("Selected constraint: %s/n", constraint);
		textLog.append(textToDIsplay);
		
	}

	@Override
	public void displayMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	
	

}
