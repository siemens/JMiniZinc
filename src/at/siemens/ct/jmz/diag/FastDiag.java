package at.siemens.ct.jmz.diag;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author z003pczy (Mara Rosu)
 */
public class FastDiag extends AbstractConflictDetection {

	private DiagnoseProgressCallback progressCallback;
	private List<Constraint> userConstraints;

	/**
	 * Constructor
	 * 
	 * @param mznFullFileName
	 *            The minizinc file which contains parameters, decision
	 *            variables and constraints. The constraints from this file are
	 *            the fixed ones. They must be consistent.
	 * @param userConstraints
	 *            Constraints sets by the user (Variable assignments)
	 * @param progressCallback
	 *            The callback for displaying messages on GUI
	 * @throws FileNotFoundException
	 */
	public FastDiag(String mznFullFileName, List<Constraint> userConstraints, DiagnoseProgressCallback progressCallback)
			throws FileNotFoundException {
		super(mznFullFileName);
		this.progressCallback = progressCallback;
		this.userConstraints = userConstraints;
	}

	/**
	 * Function for compute diagnoses in FastDiag
	 * 
	 * @param d
	 *            A subset from the user constraints
	 * @param c
	 *            A subset from the user constraints
	 * @param ac
	 *            user constraints
	 * @return a diagnose
	 * @throws Exception
	 */
	private List<Constraint> fd(List<Constraint> d, List<Constraint> c, List<Constraint> ac) throws Exception {
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

		if (constraintsSetC.isEmpty()){
			return Collections.emptyList();
		} else if (consistencyChecker.isConsistent(constraintsSetC, mznFile)) {
			progressCallback.displayMessage("All constraints are consistent. No solution can be found");
			return Collections.emptyList();
		} else {
			return fd(Collections.emptyList(), Collections.unmodifiableList(constraintsSetC),
					Collections.unmodifiableList(constraintsSetC));
		}

	}

	public void diagnose() throws Exception {
		List<Constraint> diagnoseFound = getMinConflictSet(userConstraints);
		if (!diagnoseFound.isEmpty()) {
			progressCallback.diagnoseFound(diagnoseFound);
		}
	}

}
