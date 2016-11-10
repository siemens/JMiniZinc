package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.AbstractConflictDetection;
import at.siemens.ct.jmz.conflictDetection.DebugUtils;
import at.siemens.ct.jmz.conflictDetection.SimpleConflictDetection;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class HSDAG {		
	private AbstractConflictDetection conflictDetection;
	private List<Constraint> userConstraints;	
	private String mznFullFileName;
	private DiagnoseProgressCallback progressCallback;  
	
	public HSDAG(String mznFullFileName,
			List<Constraint> userConstraints,
			DiagnoseProgressCallback progressCallback) throws FileNotFoundException {
		super();
		
		File mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}
		
		this.mznFullFileName = mznFullFileName;
		this.userConstraints = userConstraints;
		this.progressCallback = progressCallback; 
		this.conflictDetection = new SimpleConflictDetection(mznFullFileName); // todo:
																				// Modify
																				// to
																				// use
																				// other
																				// algorithm
	}
	
	public void diagnose() throws Exception{			
		DebugUtils.logLabel = "HSDAG";		
		DebugUtils.writeOutput("***********************************************");
		DebugUtils.printConstraintsSet("User Constraints Set:", userConstraints);
		DebugUtils.printFile(mznFullFileName);
		DebugUtils.writeOutput("***********************************************");
		
		List<Constraint> minCS = conflictDetection.getMinConflictSet(userConstraints);
		
		if (minCS == null){
			DebugUtils.writeOutput("A minimal conflict set does not exist.");
			if (progressCallback != null) progressCallback.displayMessage("A minimal conflict set does not exist for the user-set constraints.");			
			return;
		}
		
	 	if (progressCallback != null) progressCallback.minConflictSetFound(minCS);	 	
		
		TreeNode rootNode = new TreeNode(minCS, userConstraints);
		DiagnosesCollection diagnosesCollection = new DiagnosesCollection(); // Here are stored the diagnoses
		
		buildDiagnosesTree(rootNode, diagnosesCollection);
	}
	
	private void buildDiagnosesTree(TreeNode root, DiagnosesCollection diagnosesCollection) throws Exception {
		List<Constraint> minCS;
		List<Constraint> difference;
		TreeNode treeNode;			
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
			if (progressCallback != null) progressCallback.constraintSelected(constraint);
			
			difference = elminateConstraintFromList(root.getInitialConstraintsSet(), constraint);
			minCS = conflictDetection.getMinConflictSet(difference);
			
			DebugUtils.writeOutput("Selected constraint: " + constraint.getConstraintName());
			
			if (minCS == null) {				
				treeNode = new TreeNode(null, null);
				root.addChild(constraint, treeNode);
				List<Constraint> diagnose = getDiagnose(treeNode);
				Collections.reverse(diagnose);				
								
				if (!diagnosesCollection.Contains(diagnose)){
					diagnosesCollection.add(diagnose);
					if (progressCallback != null) progressCallback.diagnoseFound(diagnose);									
					DebugUtils.writeOutput("No min conflict set found.");
					DebugUtils.printConstraintsSet("DIAGNOSE: ", diagnose);
					
				} else {
					DebugUtils.writeOutput("Ignore diagnose:" + diagnoseToString(diagnose));
				}
			} else {
				if (progressCallback != null) progressCallback.minConflictSetFound(minCS);
				
				DebugUtils.printConstraintsSet("MIN ConflictSet:", minCS);
				DebugUtils.printConstraintsSet("Difference:", difference);
				
				treeNode = new TreeNode(minCS, difference);				
				root.addChild(constraint, treeNode);
				conflicts.add(treeNode);				
			}			
		}
		
		for(TreeNode node : conflicts){
			buildDiagnosesTree(node, diagnosesCollection);			
		}
		
		DebugUtils.indent--;			
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