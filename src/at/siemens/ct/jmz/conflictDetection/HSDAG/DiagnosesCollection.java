package at.siemens.ct.jmz.conflictDetection.HSDAG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

public class DiagnosesCollection extends ArrayList<List<Constraint>>{	
	private static final long serialVersionUID = 8940404145930730128L;

	public DiagnosesCollection() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiagnosesCollection(Collection<? extends List<Constraint>> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	public DiagnosesCollection(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	public boolean Contains(List<Constraint> diagnose){
		for(List<Constraint> d : this){
			boolean res = haveTheSameContent(diagnose, d);
			if (res) return true;
		}
		return false;
	}
	
	private boolean haveTheSameContent(List<Constraint> d1, List<Constraint> d2){
		if (d1.size() != d2.size()) return false;
		
		for(Constraint c: d1){
			if (!d2.contains(c)) return false;
		}
		
		return true;  
	}
}
