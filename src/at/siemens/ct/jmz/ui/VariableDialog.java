package at.siemens.ct.jmz.ui;

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
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import javax.swing.JOptionPane;

import at.siemens.ct.jmz.diag.FastDiag;
import at.siemens.ct.jmz.diag.hsdag.ConflictDetectionAlgorithm;
import at.siemens.ct.jmz.diag.hsdag.HSDAG;
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
import at.siemens.ct.jmz.mznparser.MiniZincCP;
import at.siemens.ct.jmz.mznparser.MiniZincElementFactory;

public class VariableDialog {

	private List<DecisionVariableGUI> mapWithControls;
	private ArrayList<Variable<?, ?>> decisionVariables;
	private File mznFile;
	private MiniZincCP mznCp;
	private List<Constraint> userConstraints;

	private TextArea textLog;
	private Button generateSolution;
	private Frame mainFrame;
	private Panel controlPanel;
	private Choice algorithmType;
	private TextComponentLogger logger;

	private final String UNDEFINED = "Undefined";
	private final String TRUE = "true";
	private final String FALSE = "false";

	private final String SCD_HSDAG = "Simple Conflict Detection - HSDAG";
	private final String QUICKXPLAIN_HSDAG = "QuickXPlain - HSDAG";
	private final String FAST_DIAG = "FastDiag";

	public VariableDialog(File mznFile) throws IOException {
		this.mznFile = mznFile;
		mznCp = new MiniZincCP(mznFile);
		decisionVariables = mznCp.getDecisionVariables();
		mapWithControls = new ArrayList<DecisionVariableGUI>();
		userConstraints = new ArrayList<Constraint>();

		prepareGUI();
		addDecisionVariables();
		addComponents();

		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

	}

	public static void main(String[] args) {

		try {

			final String PROPERTY_FILENAME = "filename";
			String mznFileName = System.getProperty(PROPERTY_FILENAME, "").toString();
			File mznFile = new File(mznFileName);
			if (!mznFile.exists()) {
				JOptionPane.showMessageDialog(null, String.format("The file \"%s\" does not exist!", mznFileName),
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			new VariableDialog(mznFile);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Input file mising", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void prepareGUI() {

		final String FRAME_TITLE = "Minimal diagnoses";
		mainFrame = new Frame(FRAME_TITLE);
		mainFrame.setLayout(new FlowLayout());

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(controlPanel);

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
		algorithmType.add(QUICKXPLAIN_HSDAG);
		algorithmType.add(SCD_HSDAG);
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

		textLog = new TextArea();
		textLog.setEditable(false);
		textLog.setPreferredSize(new Dimension(500, 700));

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(textLog);

		controlPanel.add(panel);
	}

	private void addDecisionVariables() {

		Panel dvPanel = new Panel();
		dvPanel.setMaximumSize(new Dimension(300, 500));

		GroupLayout dvlayout = new GroupLayout(dvPanel);
		dvlayout.setAutoCreateGaps(true);
		dvlayout.setAutoCreateContainerGaps(true);

		ParallelGroup groupForElementsInLine = dvlayout.createParallelGroup(Alignment.LEADING);
		SequentialGroup groupForElementsInColumns = dvlayout.createSequentialGroup();

		Component textField = null;
		Label label;

		for (Element decisionVariable : decisionVariables) {

			Variable<?, ?> var = (Variable<?, ?>) decisionVariable;
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
				variableValue.add(TRUE);
				variableValue.add(FALSE);
				variableValue.add(UNDEFINED);
				textField = variableValue;

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

	private List<Constraint> getAllValuesFromTheInterface() throws Exception {

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

	private Constraint createContraint(String variableName, String value) throws Exception {
		TypeInst<?, ?> variable = mznCp.getDecisionVariableByName(variableName);
		BooleanExpression expression;

		if (variable instanceof IntegerVariable) {
			IntegerVariable integervariable = (IntegerVariable) variable;

			if (!MiniZincElementFactory.isNumeric(value))
				throw new Exception("Wrong value inserted for variable " + variableName + ". His value must be an integer.");
				
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

	private void generateSolution()  {
		textLog.setText(null);

		

		try {
			
			userConstraints = getAllValuesFromTheInterface();

			if (userConstraints.isEmpty()) {
				JOptionPane.showMessageDialog(controlPanel, "You must set some values for decision variables!",
						"Information", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			String selectedAlgorithm = algorithmType.getSelectedItem();
			HSDAG hsdag;
			logger = new TextComponentLogger(textLog, userConstraints);
			switch (selectedAlgorithm) {
			case SCD_HSDAG:
				hsdag = new HSDAG(mznFile.getAbsolutePath(), userConstraints, logger,
						ConflictDetectionAlgorithm.SimpleConflictDetection);
				logger.displayStartMessage(mznFile);
				hsdag.diagnose();
				break;
			case QUICKXPLAIN_HSDAG:
				hsdag = new HSDAG(mznFile.getAbsolutePath(), userConstraints, logger,
						ConflictDetectionAlgorithm.QuickXPlain);
				logger.displayStartMessage(mznFile);
				hsdag.diagnose();
				break;
			case FAST_DIAG:
				FastDiag fastDiag = new FastDiag(mznFile.getAbsolutePath(), userConstraints, logger);
				logger.displayStartMessage(mznFile);
				fastDiag.diagnose();
			}

		} catch (Exception ex) {

			JOptionPane.showMessageDialog(controlPanel, String.format("An error occured! %s", ex.getMessage()), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
