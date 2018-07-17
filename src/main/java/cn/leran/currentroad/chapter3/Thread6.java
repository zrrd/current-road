package cn.leran.currentroad.chapter3;

import java.util.concurrent.locks.LockSupport;

/**
 * lockSupport 线程阻塞
 *
 * @author shaoyijiong
 * @date 2018/7/17
 */
public class Thread6 {

  public static final Object u = new Object();
  static ChangeObjectThread t1 = new ChangeObjectThread("T1");
  static ChangeObjectThread t2 = new ChangeObjectThread("T2");

  public static class ChangeObjectThread extends Thread {

    public ChangeObjectThread(String name) {
      super.setName(name);
    }

    @Override
    public void run() {
      synchronized (u) {
        System.out.println("in " + getName());
        //阻塞当前线程
        LockSupport.park();
        System.out.println(getName()+"线程结束了");
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    t1.start();
    Thread.sleep(1000);
    t2.start();
    Thread.sleep(1000);
    System.out.println("主线程");
    //解除线程阻塞
    LockSupport.unpark(t1);
    LockSupport.unpark(t2);
    t1.join();
    t2.join();
  }

}
