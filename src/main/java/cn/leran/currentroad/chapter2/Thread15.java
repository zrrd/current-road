package cn.leran.currentroad.chapter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author 邵益炯
 * @date 2018/11/5
 */
public class Thread15 {

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(10);

    //定义一个信号量  5个准入
    Semaphore semaphore = new Semaphore(5);

    Runnable longRunningTask = () -> {
      boolean permit = false;
      try {
        //获得准入许可 非阻塞的方式
        permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
        //阻塞方式
        //semaphore.acquire();
        //permit = true;
        if (permit) {
          System.out.println("Semaphore acquired");
          TimeUnit.SECONDS.sleep(5);
        } else {
          System.out.println("Could not acquire semaphore");
        }
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      } finally {
        if (permit) {
          semaphore.release();
        }
      }
    };
    IntStream.range(0, 10).forEach(i -> executor.submit(longRunningTask));

    //executor.shutdownNow();
  }
}
