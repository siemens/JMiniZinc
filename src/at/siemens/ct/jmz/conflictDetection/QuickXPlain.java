package at.siemens.ct.jmz.conflictDetection;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public class QuickXPlain extends AbstractConflictDetection{

	public QuickXPlain(String mznFullFileName) throws FileNotFoundException {
		super(mznFullFileName);		
	}

	@Override
	public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception {
		
		DebugUtils.enabled = false;
		String oldLogLabel = DebugUtils.logLabel; 
		DebugUtils.logLabel = "QuickXPlain:";
		DebugUtils.writeOutput("**********************************************************");
		DebugUtils.printConstraintsSet("* Get MinConflictSet for the following constraints", constraintsSetC);
		DebugUtils.writeOutput("* File: " + mznFile.getAbsolutePath());
		DebugUtils.writeOutput("**********************************************************");
		
		try{
			if (constraintsSetC.size() == 0) return null;
			
			List<Constraint> setD = new ArrayList<Constraint>(); 
			List<Constraint> selectedC = new ArrayList<Constraint>(); 
			
			List<Constraint> minCS = quickXPlain(setD, constraintsSetC, selectedC);
			
			DebugUtils.printConstraintsSet("MinCS = ", minCS);
			return minCS;
		} finally{
			DebugUtils.writeOutput("**********************************************************");
			DebugUtils.logLabel = oldLogLabel;
			DebugUtils.enabled = true;
		}
	}
	
	private List<Constraint> quickXPlain(List<Constraint> setD, List<Constraint> setC, List<Constraint> selectedC) throws Exception{
		boolean isConsistent = consistencyChecker.isConsistent(selectedC, mznFile); 
		if ( ! isConsistent) return null; 
		
		int q = setC.size();
		if (q == 1){
			return setC;
		}
		
		int k = q/2;
		List<Constraint> setC1 = new ArrayList<>();
		for(int i = 0; i < k; i++){
			setC1.add(setC.get(i)); 
		}
		
		List<Constraint> setC2 = new ArrayList<>();
		for(int i = k; i < q; i++){
			setC2.add(setC.get(i)); 
		}
		
		List<Constraint> CS1 = quickXPlain(setC2,  setC1, setC2);
		
		if (CS1 == null) CS1 = new ArrayList<>();		
		List<Constraint> CS2 = quickXPlain(CS1,  setC2, CS1);		
		return appendSets(CS1, CS2); 
	}
	
	private List<Constraint> appendSets(List<Constraint> CS1, List<Constraint> CS2){
		List<Constraint> reunion = new ArrayList<>(CS1);
		if (CS2 == null) return reunion; 
			
		for (Constraint c : CS2) {
			if (!reunion.contains(c)){
				reunion.add(c);
			}
		}
		return reunion;
	}
}
