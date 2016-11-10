package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

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
		StringBuilder sb = new StringBuilder();
		for (List<Constraint> diagnose : this){			
			for(Constraint c : diagnose){
				sb.append("(").append(c.getConstraintName()).append(") ");
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	
}
