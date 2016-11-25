/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * @author © Siemens AG, 2016
 */
public abstract class AbstractConflictDetection {
	protected File mznFile;
	protected ConsistencyChecker consistencyChecker;

	public AbstractConflictDetection(String mznFullFileName) throws FileNotFoundException {
		mznFile = new File(mznFullFileName);
		if (!mznFile.exists()) {
			throw new FileNotFoundException("Cannot find the file " + mznFile.getAbsolutePath());
		}

		consistencyChecker = new ConsistencyChecker();
	}

	// TODO: Shall use Set instead of List?
	public abstract List<Constraint> getMinConflictSet(List<Constraint> constraintsSetC) throws Exception;

	public static List<Constraint> appendSets(List<Constraint> CS1, List<Constraint> CS2) {
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

	public static List<Constraint> diffSets(List<Constraint> ac, List<Constraint> c2) {
		List<Constraint> diff = new ArrayList<Constraint>();
		for (Constraint constraint : ac) {
			if (!c2.contains(constraint)) {
				diff.add(constraint);
			}

		}
		return diff;
	}
}
