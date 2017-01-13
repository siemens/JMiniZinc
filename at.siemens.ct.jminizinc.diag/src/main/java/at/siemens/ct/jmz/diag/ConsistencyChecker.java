/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.File;
import java.util.List;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.elements.include.IncludeItem;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;
import at.siemens.ct.jmz.executor.IExecutor;
import at.siemens.ct.jmz.executor.PipedMiniZincExecutor;
import at.siemens.ct.jmz.writer.IModelWriter;
import at.siemens.ct.jmz.writer.ModelWriter;

/**
 * @author © Siemens AG, 2016
 */
public class ConsistencyChecker {

	private static final String Unsatisfiable = "=====UNSATISFIABLE=====";

	IModelBuilder modelBuilder;
	IModelWriter modelWriter;
	IExecutor executor;

	private void initialization() {
		modelBuilder = new ModelBuilder();
		modelWriter = new ModelWriter(modelBuilder);
		modelWriter.setSolvingStrategy(SolvingStrategy.SOLVE_SATISFY);
		executor = new PipedMiniZincExecutor("consistencyChecker", modelWriter);
	}

	/** Determines the consistency of a mzn file (Background KB) 
	 * @param mznFile - the mzn file which contains the background KB
	 * @return true if is consistent
	 * @throws Exception
	 */
	public boolean isConsistent(File mznFile) throws Exception {
		initialization();

		modelBuilder.reset();

		IncludeItem includeItem = new IncludeItem(mznFile);
		modelWriter.addIncludeItem(includeItem);

		String res = callExecutor();

		return (isSolverResultConsistent(res));
	}

	/** Determines the consistency of given constraints set with the background KB
	 * @param constraintsSet - the given constraints set
	 * @param mznFile - the mzn file which contains the background KB
	 * @return true if the given set is consistent with background KB
	 * @throws Exception
	 */
	public boolean isConsistent(List<Constraint> constraintsSet, File mznFile) throws Exception {
		initialization();
		modelBuilder.reset();

		IncludeItem includeItem = new IncludeItem(mznFile);
		modelWriter.addIncludeItem(includeItem);

		modelBuilder.add(constraintsSet);

		String res = callExecutor();

		return (isSolverResultConsistent(res));
	}

	public boolean isConsistent(List<Constraint> constraintsSet, List<Element> decisionVariables) throws Exception {
		initialization();
		modelBuilder.reset();
		modelBuilder.add(constraintsSet);
		modelBuilder.add(decisionVariables);

		String res = callExecutor();
		return (isSolverResultConsistent(res));
	}

	private boolean isSolverResultConsistent(String result) {
		boolean res = !result.contains(Unsatisfiable);
		return res;
	}

	private String callExecutor() throws Exception {
		executor.startProcess();
		executor.waitForSolution();
		int lastExitCode = executor.getLastExitCode();
		if (lastExitCode != IExecutor.EXIT_CODE_SUCCESS) {
			if (executor.getLastSolverErrors().isEmpty()) {
				throw new Exception("An error occured while running the solver. Some libraries are missing.");
			} else {
				throw new Exception("An error occured while running the solver." + executor.getLastSolverErrors());
			}

		}

		String lastSolverOutput = executor.getLastSolverOutput();
		return lastSolverOutput;
	}
}
