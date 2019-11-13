package cn.leran.currentroad.chapter2.advanced;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomUtils;

/**
 * 多线程结果获取优化
 *
 * @author shaoyijiong
 * @date 2019/11/13
 */
public class CompletionServiceGo {

  private static ExecutorService executor = Executors.newFixedThreadPool(10);
  private static Callable<String> callable = () -> {
    int i = RandomUtils.nextInt(0, 10);
    TimeUnit.SECONDS.sleep(i);
    return Thread.currentThread().getName() + "---sleep" + i;
  };


  /**
   * <pre>
   * 输出顺序
   * pool-1-thread-1---sleep8
   * pool-1-thread-2---sleep7
   * pool-1-thread-3---sleep1
   * 可以看到可能任务先完成的但是没有先输出
   * </pre>
   */
  private static void t1() throws ExecutionException, InterruptedException {

    FutureTask<String> future1 = new FutureTask<>(callable);
    FutureTask<String> future2 = new FutureTask<>(callable);
    FutureTask<String> future3 = new FutureTask<>(callable);
    executor.submit(future1);
    executor.submit(future2);
    executor.submit(future3);
    System.out.println(future1.get());
    System.out.println(future2.get());
    System.out.println(future3.get());
    executor.shutdown();
  }

  /**
   * <pre>
   * 利用CompletionService 解决上述问题 内部通过一个阻塞队列实现
   * pool-1-thread-2---sleep2
   * pool-1-thread-1---sleep5
   * pool-1-thread-3---sleep8
   * </pre>
   */
  private static void t2() throws ExecutionException, InterruptedException {
    CompletionService<String> cs = new ExecutorCompletionService<>(executor);
    Future<String> future1 = cs.submit(callable);
    Future<String> future2 = cs.submit(callable);
    Future<String> future3 = cs.submit(callable);
    for (int i = 0; i < 3; i++) {
      System.out.println(cs.take().get());
    }
    executor.shutdown();
  }


  public static void main(String[] args) throws ExecutionException, InterruptedException {
    t2();
  }
}
