package at.siemens.ct.jmz.conflictDetection;

import java.io.FileNotFoundException;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public class QuickXPlain extends AbstractConflictDetection{

	public QuickXPlain(String mznFullFileName) throws FileNotFoundException {
		super(mznFullFileName);		
	}

	@Override
	public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception {
		
		return null;
	}

}
