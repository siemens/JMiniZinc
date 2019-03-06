/**
 * Copyright Siemens AG, 2016-2019
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;
import at.siemens.ct.jmz.executor.IExecutor;
import at.siemens.ct.jmz.executor.PipedMiniZincExecutor;
import at.siemens.ct.jmz.writer.IModelWriter;
import at.siemens.ct.jmz.writer.ModelWriter;

/**
 * @author Copyright Siemens AG, 2016-2019
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
		executor = new PipedMiniZincExecutor("consistencyChecker");
	}

  public boolean isConsistent(Collection<? extends Element> fixedElements) throws DiagnosisException {
    initialization();
    modelBuilder.reset();

    modelBuilder.add(fixedElements);

    String res = callExecutor();
    return (isSolverResultConsistent(res));
	}

  /**
   * Determines the consistency of given constraints set with the background KB
   * @param constraintsSet the given constraints set
   * @param fixedModel the background KB
   * @return {@code true} if the given set is consistent with background KB
   * @throws DiagnosisException
   */
  public boolean isConsistent(Set<Constraint> constraintsSet, Collection<? extends Element> fixedModel)
      throws DiagnosisException {
		initialization();
		modelBuilder.reset();

		modelBuilder.add(fixedModel);
		modelBuilder.add(constraintsSet);

		String res = callExecutor();

		return (isSolverResultConsistent(res));
	}

  public boolean isConsistent(Set<Constraint> constraintsSet, List<Element> decisionVariables)
      throws DiagnosisException {
		initialization();
		modelBuilder.reset();
		modelBuilder.add(constraintsSet);
		modelBuilder.add(decisionVariables);

		String res = callExecutor();
		return (isSolverResultConsistent(res));
	}

	private boolean isSolverResultConsistent(String result) {
    return !result.contains(Unsatisfiable);
	}

  private String callExecutor() throws DiagnosisException {
    try {
      executor.startProcess(modelWriter);
      executor.waitForSolution();
    } catch (IOException e) {
      throw new DiagnosisException("Solver could not be started", e);
    } catch (InterruptedException e) {
    }

		int lastExitCode = executor.getLastExitCode();
		if (lastExitCode != IExecutor.EXIT_CODE_SUCCESS) {
			if (executor.getLastSolverErrors().isEmpty()) {
        throw new DiagnosisException("An error occured while running the solver. Some libraries are missing.");
			} else {
        throw new DiagnosisException("An error occured while running the solver." + executor.getLastSolverErrors());
			}
		}

    return executor.getLastSolverOutput();
	}
}
