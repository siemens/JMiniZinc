/**
 * Copyright Siemens AG, 2016, 2019
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.executor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A factory for {@link Executor}s
 * 
 * @author Copyright Siemens AG, 2016, 2019
 *
 * @param <E>
 *          the type of Executors produced by this factory
 */
public class ExecutorFactory<E extends Executor> {

  private Class<E> executorClass;

  public ExecutorFactory(Class<E> executorClass) {
    this.executorClass = executorClass;
  }

  public E createExecutor(String identifier, MiniZincSolver miniZincSolver) {
    try {
      Constructor<E> constructor = executorClass.getConstructor(String.class, MiniZincSolver.class);
      return constructor.newInstance(identifier, miniZincSolver);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

  public E createExecutor(String identifier, FlatZincSolver flatZincSolver) {
    try {
      Constructor<E> constructor = executorClass.getConstructor(String.class, FlatZincSolver.class);
      return constructor.newInstance(identifier, flatZincSolver);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

}
