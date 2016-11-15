package at.siemens.ct.jmz.conflictDetection.fastDiag;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.conflictDetection.AbstractConflictDetection;
import at.siemens.ct.jmz.conflictDetection.HSDAG.DiagnoseProgressCallback;
import at.siemens.ct.jmz.elements.constraints.Constraint;

public class FastDiag extends AbstractConflictDetection {

	private DiagnoseProgressCallback progressCallback;
	private List<Constraint> userConstraints;

	public FastDiag(String mznFullFileName, List<Constraint> userConstraints, DiagnoseProgressCallback progressCallback)
			throws FileNotFoundException {
		super(mznFullFileName);
		this.progressCallback = progressCallback;
		this.userConstraints = userConstraints;
	}

	private List<Constraint> fd(List<Constraint> d, List<Constraint> c, List<Constraint> ac) throws Exception {
		// removeFromAC = appendSets(removeFromAC, d);
		if (!d.isEmpty() && consistencyChecker.isConsistent(ac, mznFile)) {
			return Collections.emptyList();
		}

		int q = c.size();
		if (q == 1) {
			return new ArrayList<Constraint>(c);
		}

		int k = q / 2;
		List<Constraint> c1 = c.subList(0, k);
		List<Constraint> c2 = c.subList(k, q);

		List<Constraint> d1 = fd(c2, c1, diffSets(ac, c2));
		List<Constraint> d2 = fd(d1, c2, diffSets(ac, d1));
		return appendSets(d1, d2);
	}

	@Override
	public List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception {
		// TODO Auto-generated method stub
		return fd(Collections.emptyList(), Collections.unmodifiableList(constraintsSetC),
				Collections.unmodifiableList(constraintsSetC));
	}

	public void diagnose() throws Exception {
		List<Constraint> diagnoseFound = getMinConflictSet(userConstraints);
		progressCallback.diagnoseFound(diagnoseFound);
	}

}