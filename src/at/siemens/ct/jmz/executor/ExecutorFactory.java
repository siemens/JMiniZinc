package at.siemens.ct.jmz.executor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * A factory for {@link Executor}s
 * 
 * @author z003ft4a (Richard Taupe)
 *
 * @param <E>
 *          the type of Executors produced by this factory
 */
public class ExecutorFactory<E extends Executor> {

  private Class<E> executorClass;

  public ExecutorFactory(Class<E> executorClass) {
    this.executorClass = executorClass;
  }

  public E createExecutor(String identifier, IModelWriter modelWriter) {
    try {
      Constructor<E> constructor = executorClass.getConstructor(String.class, IModelWriter.class);
      return constructor.newInstance(identifier, modelWriter);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

}
