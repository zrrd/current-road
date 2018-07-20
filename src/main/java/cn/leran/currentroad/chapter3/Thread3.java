package cn.leran.currentroad.chapter3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread3 implements Runnable {

  public static ReentrantLock lock = new ReentrantLock();
  //获得当前锁绑定的 condition
  public static Condition condition = lock.newCondition();

  @Override
  public void run() {
    try {
      lock.lock();
      condition.await();
      System.out.println("Thread is going on");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) throws InterruptedException {

    Thread3 thread3 = new Thread3();
    Thread t = new Thread(thread3);
    t.start();
    Thread.sleep(2000);
    lock.lock();
    condition.signal();
    System.out.println("那个线程激活 还会去获得那个lock");
    Thread.sleep(2000);
    lock.unlock();
  }
}
