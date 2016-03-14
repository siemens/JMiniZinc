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
   * Runs the given {@code task} for at most {@code timeout} seconds.<br>
   * If {@code timeout} is {@code null}, the task is executed without any time limit.
   */
  public static <T> T limitSeconds(Callable<T> task, Integer timeout) throws Exception {
    Long timeoutMs = timeout == null ? null : Long.valueOf(timeout) * 1000;
    return limitMilliseconds(task, timeoutMs);
  }

  /**
   * Runs the given {@code task} for at most {@code timeout} milliseconds.<br>
   * If {@code timeout} is {@code null}, the task is executed without any time limit.
   */
  public static <T> T limitMilliseconds(Callable<T> task, Long timeout) throws Exception {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<T> future = executor.submit(task);
    executor.shutdown();
    if (timeout != null) {
      executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
      executor.shutdownNow();
    }
    try {
      return future.get();
    } catch (ExecutionException e) {
      e.printStackTrace();
      Throwable cause = e.getCause();
      throw cause instanceof Exception ? (Exception) cause : e;
    }
  }

}
