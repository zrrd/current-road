package cn.leran.currentroad.chapter2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition.
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread3 implements Runnable {

  private static ReentrantLock lock = new ReentrantLock();
  /**
   * 获得当前锁绑定的 condition Condition 则是将 wait、notify、notify、notifyAll等操作转化为响应的对象,
   * 将复杂而晦涩的同步操作转变为直观可控的对象行为.
   */

  private static Condition condition = lock.newCondition();

  @Override
  public void run() {
    try {
      lock.lock();
      //相当于Object的wait()方法 造成当前线程在接到信号或被中断之前一直处于等待状态。
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
    //新建一个线程 启动
    Thread t = new Thread(thread3);
    t.start();

    //暂停当前线程2s  让上面那个线程走完
    Thread.sleep(2000);
    lock.lock();
    //相当于Object的notify()方法  唤醒一个等待线程。
    condition.signal();
    System.out.println("那个线程激活 还会去获得那个lock");
    Thread.sleep(2000);
    lock.unlock();
  }
}
