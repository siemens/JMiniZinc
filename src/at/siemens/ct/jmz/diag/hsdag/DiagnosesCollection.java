package at.siemens.ct.jmz.diag.hsdag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.diag.DiagnoseMetadata;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * This class stores all diagnoses for a configuration problem. 
 */
public class DiagnosesCollection extends ArrayList<List<Constraint>>{	
	private static final long serialVersionUID = 8940404145930730128L;

	public DiagnosesCollection() {
		super();		
	}

	public DiagnosesCollection(Collection<? extends List<Constraint>> c) {
		super(c);		
	}

	public DiagnosesCollection(int initialCapacity) {
		super(initialCapacity);		
	}

	public DiagnoseMetadata Contains(List<Constraint> diagnose){
		for(List<Constraint> d : this){			
			DiagnoseMetadata diagnoseMetadata = compareDiagnose(diagnose, d);
			if (diagnoseMetadata != DiagnoseMetadata.Min) return diagnoseMetadata;
		}
		return DiagnoseMetadata.Min;
	}
		
	private DiagnoseMetadata compareDiagnose(List<Constraint> newDiagnose, List<Constraint> existingDiagnose){
		for(Constraint c: existingDiagnose){
			if (!newDiagnose.contains(c)) return DiagnoseMetadata.Min;
		}
		
		if (newDiagnose.size() == existingDiagnose.size()) return DiagnoseMetadata.AlreadyExists;
		return DiagnoseMetadata.NotMin;
	}
		
	public java.lang.String toString() {		
		List<String> lines = new ArrayList<String>();
		
		for (List<Constraint> diagnose : this){
			
			List<String> constraints = new ArrayList<String>();
			for(Constraint c : diagnose){
				constraints.add("(" + c.getConstraintName() + ") ");
			}
			
			Collections.sort(constraints);
			StringBuilder line = new StringBuilder();
			for(String c : constraints){
				line.append(c); 
			}
			lines.add(line.toString());
		}
		
		StringBuilder sb = new StringBuilder();
		Collections.sort(lines);
		for(String line : lines){
			sb.append(line).append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	
}
