package at.siemens.ct.jmz.conflictDetection;

import java.awt.TextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public class HSDAG {
	private String mznFullFileName;
	private AbstractConflictDetection conflictDetection;
	private List<Constraint> userConstraints;
	private TextArea textArea;

	public HSDAG(String mznFullFileName, List<Constraint> userConstraints,TextArea textArea) throws FileNotFoundException {
		super();
		File mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}
		this.userConstraints = userConstraints;
		this.mznFullFileName = mznFullFileName;
		this.textArea = textArea;
	}
	
	public HSDAG(String mznFullFileName, List<Constraint> userConstraints) throws FileNotFoundException {
		super();
		File mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}
		this.userConstraints = userConstraints;
		this.mznFullFileName = mznFullFileName;
	}

	public void generateAcyclicGraphWithSCD() throws Exception {

		conflictDetection = new SimpleConflictDetection(mznFullFileName);
		List<Constraint> firstCS = conflictDetection.getMinConflictSet(userConstraints);
		List<Constraint> reducedKb = new ArrayList<Constraint>();
		for (Constraint constraint : firstCS) {
			reducedKb = eliminateConstraint(constraint);
			conflictDetection.getMinConflictSet(reducedKb);
			if(reducedKb!=null)
			{
				System.out.println(reducedKb.toString());
			}

		}

	}
	
	private List<Constraint> eliminateConstraint(Constraint c)
	{
		List<Constraint> reducedKb = new ArrayList<Constraint>();
		
		for (Constraint constraint : userConstraints) {
			if(!constraint.getConstraintName().equals(c.getConstraintName()))
			{
				reducedKb.add(constraint);
			}
		}
		return reducedKb;
		
	}

}
