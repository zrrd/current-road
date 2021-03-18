package cn.leran.currentroad.chapter4;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shaoyijiong
 * @date 2021/3/18
 */
@SuppressWarnings("all")

public class Test3 {


  /**
   * 三个线程循环打印 并保证打印的顺序
   *
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    AtomicBoolean first = new AtomicBoolean(true);
    AtomicBoolean next = new AtomicBoolean(true);
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    Thread threadX = new Thread(() -> {
      lock.lock();
      try {
        for (int i = 0; i < 10; i++) {
          System.out.println("X");
          first.set(false);
          condition2.signal();
          try {
            condition1.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println(1);
      } finally {
        condition2.signal();
        lock.unlock();
      }

    });

    Thread threadY = new Thread(() -> {
      while (first.get()) {
        Thread.yield();
      }
      lock.lock();
      try {
        for (int i = 0; i < 10; i++) {
          System.out.println("Y");
          next.set(false);
          condition3.signal();
          try {
            condition2.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println(2);
      } finally {
        condition3.signal();
        lock.unlock();
      }

    });

    Thread threadZ = new Thread(() -> {
      while (next.get()) {
        Thread.yield();
      }
      lock.lock();
      try {
        for (int i = 0; i < 10; i++) {
          System.out.println("Z");
          condition1.signal();
          try {
            condition3.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println(3);
      } finally {
        condition1.signal();
        lock.unlock();
      }

    });

    threadX.start();
    threadY.start();
    threadZ.start();


    threadX.join();
    threadY.join();
    threadZ.join();
  }
}
