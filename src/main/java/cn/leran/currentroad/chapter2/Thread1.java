package cn.leran.currentroad.chapter2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 重入锁.
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
@SuppressWarnings("all")
public class Thread1 {

  /**
   * <pre>
   * 是否为公平锁 new ReentrantLock(true);
   * 非公平锁    new ReentrantLock(false);
   * </pre>
   */
  private static ReentrantLock lock = new ReentrantLock();
  public static volatile int i = 0;

  /**
   * 测试
   */
  public static void main(String[] args) throws InterruptedException {

    Thread t1 = new Thread(() -> {
      System.out.println("t0 开始获取锁");
      lock.lock();
      System.out.println("t0 成功获取锁");
      for (int j = 0; j < 1000; j++) {
        i++;
      }
      try {
        Thread.sleep(3_000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
        System.out.println("t0 成功释放锁");
      }
    });

    Thread t2 = new Thread(() -> {
      System.out.println("t1 开始获取锁");
      if (lock.tryLock()) {
        System.out.println("t1 成功获取锁");
        for (int j = 0; j < 1000; j++) {
          i++;
        }
        lock.unlock();
        System.out.println("t1 成功释放锁");
      } else {
        System.out.println("t1 获取锁失败");
      }
    });

    Thread t3 = new Thread(() -> {
      System.out.println("t2 开始获取锁");
      try {
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
          System.out.println("t2 成功获取锁");
          for (int j = 0; j < 1000; j++) {
            i++;
          }
          lock.unlock();
          System.out.println("t2 成功释放锁");
        } else {
          System.out.println("t2 获取锁失败");
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    t1.start();
    t2.start();
    t3.start();

    t1.join();
    t2.join();
    t3.join();
    System.out.println(i);
  }
}
