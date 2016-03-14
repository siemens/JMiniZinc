package at.siemens.ct.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Provides simple methods to use threads easily.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ThreadUtils {

  /**
   * Runs the given {@code task} for at most {@code timeout} seconds.
   */
  public static <T> T limitSeconds(Callable<T> task, int timeout) throws Exception {
    return limitMilliseconds(task, timeout * 1000);
  }

  /**
   * Runs the given {@code task} for at most {@code timeout} milliseconds.
   */
  public static <T> T limitMilliseconds(Callable<T> task, long timeout) throws Exception {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<T> future = executor.submit(task);
    executor.shutdown();
    executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
    executor.shutdownNow();
    try {
      return future.get();
    } catch (ExecutionException e) {
      e.printStackTrace();
      Throwable cause = e.getCause();
      throw cause instanceof Exception ? (Exception) cause : e;
    }
  }

}
