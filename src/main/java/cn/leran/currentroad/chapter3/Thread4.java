package cn.leran.currentroad.chapter3;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread4 {

  private static Lock lock = new ReentrantLock();
  /**
   * 读写锁
   */
  private static ReentrantReadWriteLock readWriteLockLock = new ReentrantReadWriteLock();
  /**
   * 读锁
   */
  private static Lock readLock = readWriteLockLock.readLock();
  /**
   * 写锁
   */
  private static Lock writeLock = readWriteLockLock.writeLock();
  private int value;

  public Object handleRead(Lock lock) {
    try {
      lock.lock();
      Thread.sleep(1000);
      return value;
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return -1;
  }

  public void handleWrite(Lock lock, int index) {
    try {
      lock.lock();
      Thread.sleep(1000);
      value = index;
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    final Thread4 demo = new Thread4();
    Runnable readRunnable = () -> demo.handleRead(readLock);
    Runnable writeRunnable = () -> demo.handleWrite(writeLock, new Random().nextInt());
    for (int i = 0; i < 18; i++) {
      new Thread(readRunnable).start();
    }
    for (int i = 18; i < 20; i++) {
      new Thread(writeRunnable).start();
    }
  }
}
