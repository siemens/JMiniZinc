package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.AbstractConflictDetection;
import at.siemens.ct.jmz.conflictDetection.DebugUtils;
import at.siemens.ct.jmz.conflictDetection.SimpleConflictDetection;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class HSDAG {
	private final String newline = System.getProperty("line.separator");
	
	private AbstractConflictDetection conflictDetection;
	private List<Constraint> userConstraints;
	//private List<Element> declarations;
	
	// todo: Fo not use textArea in this class. Implement callbacks to send
	// messages to gui
	public HSDAG(String mznFullFileName,
			List<Constraint> userConstraints,
			List<Element> declarations) throws FileNotFoundException {
		super();
		File mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}
		
		this.userConstraints = userConstraints;				
		this.conflictDetection = new SimpleConflictDetection(mznFullFileName, declarations); // todo:
																				// Modify
																				// to
																				// use
																				// other
																				// algorithm
	}
	
	public String diagnose() throws Exception{
		DebugUtils.logLabel = "HSDAG";		
		List<Constraint> minCS = conflictDetection.getMinConflictSet(userConstraints);
		
		if (minCS == null){
			DebugUtils.writeOutput("A minimal conflict set does not exist.");
			return "A minimal conflict set does not exist."; // todo: Improve
																// this
		}
		
		TreeNode rootNode = new TreeNode(minCS, userConstraints);
		
		DebugUtils.writeOutput("Start with a MinConstraintsSet:" );
		DebugUtils.indent++;
		//DebugUtils.printConstraintsSet("MinCS", minCS);
		//DebugUtils.printConstraintsSet("Input constraints set", userConstraints);
		
		String res = buildDiagnosesTree(rootNode);
		//DebugUtils.writeOutput("OUTPUT = ");
		//DebugUtils.writeOutput(res);
		//DebugUtils.writeOutput("\\\\OUTPUT = ");
		return res;
	}
	
	private String buildDiagnosesTree(TreeNode root) throws Exception {
		List<Constraint> minCS;
		List<Constraint> difference;
		TreeNode treeNode;
		StringBuilder sb = new StringBuilder();		
		List<TreeNode> conflicts = new ArrayList<TreeNode>();
		
		DebugUtils.indent++;
		
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		DebugUtils.writeOutput("Build Diagnose tree for node");
		DebugUtils.indent++;
		DebugUtils.printConstraintsSet("MinCS = ", root.getData());
		DebugUtils.printConstraintsSet("Input constraints set = ", root.getInitialConstraintsSet());
		DebugUtils.indent--;
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		for (Constraint constraint : root.getData()) {					
			difference = elminateConstraintFromList(root.getInitialConstraintsSet(), constraint);
			minCS = conflictDetection.getMinConflictSet(difference);
			
			DebugUtils.writeOutput("Selected constraint: " + constraint.getConstraintName());
			
			if (minCS == null) {				
				treeNode = new TreeNode(null, null);
				root.addChild(constraint, treeNode);
				List<Constraint> diagnose =getDiagnose(treeNode);
				
				String s = diagnoseToString(diagnose);
				DebugUtils.writeOutput("No min conflict set found.");
				DebugUtils.writeOutput("DIAGNOSE: " + s);
				
				sb.append("Diagnose: ");
				sb.append(s);
				sb.append(newline);				
			} else {
				DebugUtils.printConstraintsSet("MIN ConflictSet:", minCS);
				DebugUtils.printConstraintsSet("Difference:", difference);				
				treeNode = new TreeNode(minCS, difference);				
				root.addChild(constraint, treeNode);
				conflicts.add(treeNode);
				//buildDiagnosesTree(treeNode, difference);
			}			
		}
		
		for(TreeNode node : conflicts){
			String s = buildDiagnosesTree(node);
			sb.append(s);
		}
		
		DebugUtils.indent--;
		String res = sb.toString();
		DebugUtils.writeOutput("Result = " + res);
		DebugUtils.writeOutput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return res;
	}
	
	private List<Constraint> getDiagnose(TreeNode node) {
		List<Constraint> diagnoses = new ArrayList<Constraint>();
		
		if (node.getConstraint() != null){
			diagnoses.add(node.getConstraint());
		}
		if (node.getParentNode() != null){
			diagnoses.addAll(getDiagnose(node.getParentNode()));
		}
		return diagnoses;
	}
	
	private String diagnoseToString(List<Constraint> diagnose){
		StringBuilder sb = new StringBuilder();
		int count = diagnose.size();
		for (int i = count -1; i >= 0; i--){
			Constraint c = diagnose.get(i);
			sb.append(c.getConstraintName() + "; ");
		}
		return sb.toString();
	}
			
	/*private String getDiagnoses(TreeNode rootNode) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(rootNode);
		printNode(rootNode);
		rootNode.setVisited(true);
		while(!queue.isEmpty()) {
			TreeNode node = (TreeNode)queue.remove();
			TreeNode child=null;
			while((child = getUnvisitedChildNode(node))!=null) {
				child.setVisited(true);
				printNode(child);
				queue.add(child);
			}
		}
		return "This is a test!";
	}*/

	/**
     * returns the left child if not visited, then right child if not visited
     */
    /*private TreeNode getUnvisitedChildNode(TreeNode node) {
        if (node.getLeft() != null) {
            if (!node.getLeft().isVisited()) {
                return node.getLeft();
            }
        }
        if (node.getRight() != null) {
            if (!node.getRight().isVisited()) {
                return  node.getRight();
            }
        }
        return null;
    }
    
	private void printNode(TreeNode rootNode){
		System.out.println(rootNode.toString());
	}*/

	private List<Constraint> elminateConstraintFromList(List<Constraint> constraints, Constraint constraint) {
		List<Constraint> returnList = new ArrayList<Constraint>();
		if (constraint != null) {
			for (Constraint ct : constraints) {
				if (!ct.getConstraintName().equals(constraint.getConstraintName())) {
					returnList.add(ct);
				}

			}
		}
		return returnList;
	}	
}