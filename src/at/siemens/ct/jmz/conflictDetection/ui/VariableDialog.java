package at.siemens.ct.jmz.conflictDetection.ui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.PopupMenu;
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
import javax.swing.PopupFactory;

import at.siemens.ct.jmz.conflictDetection.HSDAG.HSDAG;
import at.siemens.ct.jmz.conflictDetection.mznParser.MiniZincCP;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;

public class VariableDialog<V> {

	private Frame mainFrame;
	private Panel controlPanel;
	private MiniZincCP mznCp;
	private Map<Label, TextField> map;
	private ArrayList<Element> decisionVariables;
	private File mznFile; 
	private TextArea textLog = new TextArea();

	public VariableDialog(File mznFile) throws IOException, IllegalArgumentException, IllegalAccessException {
		this.mznFile = mznFile;
		mznCp = new MiniZincCP(mznFile);
		decisionVariables = (ArrayList<Element>) mznCp.getModelBuilder().elements().filter(e -> e.isVariable() == true)
				.collect(Collectors.toList());
		map = new HashMap<Label, TextField>();

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
				JOptionPane.showMessageDialog(null, String
						.format("The file \"%s\" does not exist!", mznFileName),
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			/*VariableDialog variableDialog =*/ new VariableDialog(mznFile);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void prepareGUI() {

		mainFrame = new Frame("Csp Debugger and Solver");
		mainFrame.setResizable(false);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setLocationByPlatform(true);

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);

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

		Choice algotithm = new Choice();
		algotithm.add("Simple Conflic Detection");
		algotithm.add("QuickXPlain");
		algotithm.add("FastDiag");
		algotithm.setEnabled(false); //todo: Temporary

		algorithmChosing.add(new Label("Chose the algorithm:"));
		algorithmChosing.add(algotithm);
		panel.add(algorithmChosing);

		Button generateSolution = new Button("Generate Solution");
		generateSolution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generateSolution();

			}

		});
		generateSolution.setMaximumSize(new Dimension(120, 30));		
		textLog.setEditable(false);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(generateSolution);
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

		TextField textField;
		Label label;

		ArrayList<Element> decisionVariables = (ArrayList<Element>) mznCp.getModelBuilder().elements()
				.filter(e -> e.isVariable() == true).collect(Collectors.toList());

		for (Element decisionVariable : decisionVariables) {

			Variable<T, V> var = (Variable<T, V>) decisionVariable;

			textField = new TextField();
			textField.setPreferredSize(new Dimension(70, 20));
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

	private List<Constraint> getAllValuesFromTheInterface() {

		ArrayList<Constraint> userConstraints = new ArrayList<Constraint>();

		for (Entry<Label, TextField> entry : map.entrySet()) {
			Constraint c = createContraint(entry.getKey().getText(), entry.getValue().getText());
			if (c!= null){
				userConstraints.add(c);
			}
		}

		return userConstraints;

	}

	private Constraint createContraint(String variableName, String value) {
		Integer variableValue = null;
		IntegerVariable variable = (IntegerVariable) mznCp.getModelBuilder().getElementByName(variableName);

		try {
			variableValue = variable.parseValue(value);
			
			BooleanExpression expression = new RelationalExpression<>(variable, RelationalOperator.EQ,
					new IntegerConstant(variableValue));
			Constraint constraint = new Constraint("userDefined", 
					String.format("%s = %s", variableName, value), expression);
			return constraint;
			
		} catch (IllegalArgumentException e) {
			/*JOptionPane.showMessageDialog(controlPanel, String
					.format("Value inserted for variable %s is incorect. Insert a value in domain", variable.getName()),
					"WARNING", JOptionPane.WARNING_MESSAGE);*/

		}
		return null;
	}

	private void generateSolution() {
		List<Constraint> userConstraints = getAllValuesFromTheInterface();
		if (userConstraints.isEmpty()){
			JOptionPane.showMessageDialog(controlPanel, "You must set some values for decision variables!",
					"Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		try{
			//todo: select algorithm according to user selection.
			HSDAG hsdag = new HSDAG(mznFile.getAbsolutePath(), userConstraints);			
			textLog.setText(hsdag.diagnose());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(controlPanel, String
					.format("An error occured! %s", ex.getMessage()),
					"Error", JOptionPane.ERROR_MESSAGE);
		}		
	}

}
