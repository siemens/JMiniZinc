package at.siemens.ct.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

  /**
   * Starts a new Thread in which the given input stream will be read to a string.
   * 
   * @param inputStream
   * @return
   */
  public static Future<String> readInThread(InputStream inputStream) {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<String> future = executor.submit(new CallableReader(inputStream));
    executor.shutdown();
    return future;
  }

  private static final class CallableReader implements Callable<String> {

    private InputStream inputStream;

    CallableReader(InputStream inputStream) {
      this.inputStream = inputStream;
    }

    @Override
    public String call() throws Exception {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
      }
    }

  }

}
