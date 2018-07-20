package cn.leran.currentroad.chapter3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 重入锁
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread1 implements Runnable {

  public static ReentrantLock lock = new ReentrantLock();
  public static int i = 0;

  @Override
  public void run() {
    for (int j = 0; j < 100000; j++) {
      //lock.lockInterruptibly();
      lock.lock();
      //lock.lock(); 可以加多次锁
      try {
        i++;
      } finally {
        lock.unlock();
        //lock.unlock(); 释放的时候也要释放相同次数的锁
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Thread1 thread1 = new Thread1();
    Thread t1 = new Thread(thread1);
    Thread t2 = new Thread(thread1);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(i);
  }
}
