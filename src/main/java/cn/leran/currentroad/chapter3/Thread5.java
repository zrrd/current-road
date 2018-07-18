package cn.leran.currentroad.chapter3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch 计数器.
 *
 * @author shaoyijiong
 * @date 2018/7/17
 */
public class Thread5 implements Runnable {

  static final CountDownLatch end = new CountDownLatch(10);
  static final Thread5 demo = new Thread5();

  @Override
  public void run() {
    try {
      //模拟检查任务
      Thread.sleep(new Random().nextInt(10) * 1000);
      System.out.println("check complete");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      end.countDown();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 10; i++) {
      executorService.submit(demo);
    }
    //等待检查
    end.await();
    //发射火箭
    System.out.println("file");
    executorService.shutdown();
  }
}
