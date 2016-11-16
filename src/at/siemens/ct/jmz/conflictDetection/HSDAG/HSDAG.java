package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.AbstractConflictDetection;
import at.siemens.ct.jmz.conflictDetection.ConflictDetectionAlgorithm;
import at.siemens.ct.jmz.conflictDetection.ConsistencyChecker;
import at.siemens.ct.jmz.conflictDetection.DebugUtils;
import at.siemens.ct.jmz.conflictDetection.QuickXPlain;
import at.siemens.ct.jmz.conflictDetection.SimpleConflictDetection;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * This class implements HSDAG algorithm for detecting all diagnoses. 
 */
public class HSDAG {		
	private AbstractConflictDetection conflictDetection;
	private List<Constraint> userConstraints;	
	private File mznFile;
	private DiagnoseProgressCallback progressCallback;  
	
	public HSDAG(String mznFullFileName,
			List<Constraint> userConstraints,
			DiagnoseProgressCallback progressCallback, 
			ConflictDetectionAlgorithm conflictDetectionAlgorithm) throws Exception {
		super();
		
		mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}
		this.userConstraints = userConstraints;
		this.progressCallback = progressCallback; 
		switch (conflictDetectionAlgorithm) {
		case SimpleConflictDetection:
			this.conflictDetection = new SimpleConflictDetection(mznFullFileName);
			break;
		case QuickXPlain:
			this.conflictDetection = new QuickXPlain(mznFullFileName);
			break;
		}
	}
	
	public DiagnosesCollection diagnose() throws Exception{			
		DebugUtils.logLabel = "HSDAG";		
		DebugUtils.writeOutput("***********************************************");
		DebugUtils.printConstraintsSet("User Constraints Set:", userConstraints);
		DebugUtils.printFile(mznFile.getAbsolutePath());
		DebugUtils.writeOutput("***********************************************");
		
		ConsistencyChecker consistencyChecker = new ConsistencyChecker(); 
		if (! consistencyChecker.isConsistent(mznFile)){
			DebugUtils.writeOutput("The input constraints set in not consistent!");
			if (progressCallback != null) progressCallback.displayMessage("The constraints set form the input file is not consistent.");
			return new DiagnosesCollection();
		};
				
		List<Constraint> minCS = conflictDetection.getMinConflictSet(userConstraints);
		
		if (minCS == null){
			DebugUtils.writeOutput("A minimal conflict set does not exist.");
			if (progressCallback != null) progressCallback.displayMessage("A minimal conflict set does not exist for the user-set constraints.");			
			return new DiagnosesCollection();
		}
		
	 	if (progressCallback != null) progressCallback.minConflictSet(minCS, userConstraints);
		
		TreeNode rootNode = new TreeNode(minCS, userConstraints);
		DiagnosesCollection diagnosesCollection = new DiagnosesCollection(); // Here are stored the diagnoses
		
		buildDiagnosesTree(rootNode, diagnosesCollection);
		
		if (progressCallback!= null) progressCallback.allDiagnoses(diagnosesCollection);
		
		return diagnosesCollection;
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
			difference = removeConstraintFromList(root.getInitialConstraintsSet(), constraint);
			
			if (progressCallback != null) {
				progressCallback.displayMessage("");
				progressCallback.constraintSelected(constraint);			
			}
			minCS = conflictDetection.getMinConflictSet(difference);
			
			DebugUtils.writeOutput("Selected constraint: " + constraint.getConstraintName());
			if (progressCallback != null) {				
				progressCallback.minConflictSet(minCS, difference);
			}
			
			if (minCS == null) {				
				treeNode = new TreeNode(null, null);
				root.addChild(constraint, treeNode);
				List<Constraint> diagnose = getDiagnose(treeNode);
				Collections.reverse(diagnose);				
					
				DiagnoseMetadata diagnoseMetadata = diagnosesCollection.Contains(diagnose); 				
				if (diagnoseMetadata == DiagnoseMetadata.Min){
					diagnosesCollection.add(diagnose);
					if (progressCallback != null) progressCallback.diagnoseFound(diagnose);									
					DebugUtils.writeOutput("No min conflict set found.");
					DebugUtils.printConstraintsSet("DIAGNOSIS: ", diagnose);					
				} else {
					DebugUtils.writeOutput("Ignore diagnosis:" + diagnoseToString(diagnose));
					if (progressCallback != null) progressCallback.ignoredDiagnose(diagnose, diagnoseMetadata);
				}
			} else {					
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

	private List<Constraint> removeConstraintFromList(List<Constraint> constraints, Constraint constraint) {
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