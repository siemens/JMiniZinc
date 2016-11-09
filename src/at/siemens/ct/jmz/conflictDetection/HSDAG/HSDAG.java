package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.AbstractConflictDetection;
import at.siemens.ct.jmz.conflictDetection.SimpleConflictDetection;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class HSDAG {
	// /private String mznFullFileName;
	private AbstractConflictDetection conflictDetection;
	private List<Constraint> userConstraints;
	// private TextArea textArea;

	// todo: Fo not use textArea in this class. Implement callbacks to send
	// messages to gui
	public HSDAG(String mznFullFileName,
			List<Constraint> userConstraints/* , TextArea textArea */) throws FileNotFoundException {
		super();
		File mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}

		this.userConstraints = userConstraints;
		// this.mznFullFileName = mznFullFileName;
		// this.textArea = textArea;

		this.conflictDetection = new SimpleConflictDetection(mznFullFileName); // todo:
																				// Modify
																				// to
																				// use
																				// other
																				// algorithm
	}

	public String diagnose() throws Exception {
		List<Constraint> minCS = conflictDetection.getMinConflictSet(userConstraints);

		if (minCS == null) {
			return "A minimal conflict set does not exist."; // todo: Improve
																// this
		}

		TreeNode rootNode = new TreeNode(minCS);
		buildDiagnosesTree(rootNode, userConstraints);

		return getDiagnoses(rootNode);
	}

	private void buildDiagnosesTree(TreeNode root, List<Constraint> parentKB) throws Exception {
		List<Constraint> minCS;
		List<Constraint> difference;
		TreeNode treeNode;

		for (Constraint constraint : root.getData()) {
			difference = elminateConstraintFromList(parentKB, constraint);
			minCS = conflictDetection.getMinConflictSet(difference);

			if (minCS == null) {
				treeNode = new TreeNode(null);
				root.addChild(constraint, treeNode);
			} else {
				treeNode = new TreeNode(minCS);
				root.addChild(constraint, treeNode);
				buildDiagnosesTree(treeNode, difference);
			}
		}
	}

	private String getDiagnoses(TreeNode rootNode) {
		// StringBuilder
		return "This is a test!";
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