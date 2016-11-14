package at.siemens.ct.jmz.conflictDetection.fastDiag;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.AbstractConflictDetection;
import at.siemens.ct.jmz.conflictDetection.HSDAG.DiagnoseProgressCallback;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class FastDiag extends AbstractConflictDetection {

	public FastDiag(String mznFullFileName, List<Constraint> userConstraints, DiagnoseProgressCallback progressCallback)
			throws FileNotFoundException {
		super(mznFullFileName);
	}

	private List<Constraint> fd(List<Constraint> d, List<Constraint> c, List<Constraint> removeFromAC)
			throws Exception {
		removeFromAC = appendSets(removeFromAC, d);
		if (d.isEmpty() != true && consistencyChecker.isConsistent(c, mznFile)) {
			return Collections.emptyList();
		}

		int q = c.size();
		if (q == 1) {
			return new ArrayList<Constraint>(c);
		}

		int k = q / 2;
		List<Constraint> c1 = c.subList(0, k); // fromIndex is inclusive,
												// toIndex is exclusive!
		List<Constraint> c2 = c.subList(k, q);

		List<Constraint> d1 = fd(c2, c1, removeFromAC);
		List<Constraint> d2 = fd(d1, c2, removeFromAC);
		return appendSets(d1, d2);
	}

	private List<Constraint> appendSets(List<Constraint> CS1, List<Constraint> CS2) {
		List<Constraint> reunion = new ArrayList<>(CS1);
		if (CS2 == null)
			return reunion;

		for (Constraint c : CS2) {
			if (!reunion.contains(c)) {
				reunion.add(c);
			}
		}
		return reunion;
	}

	@Override
	public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception {
		// TODO Auto-generated method stub
		return fd(Collections.emptyList(), Collections.unmodifiableList(constraintsSetC), Collections.emptyList());
	}

}
