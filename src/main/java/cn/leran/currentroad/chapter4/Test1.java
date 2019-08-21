package cn.leran.currentroad.chapter4;

/**
 * @author shaoyijiong
 * @date 2019/8/9
 */
public class Test1 {


  private static final Object object = new Object();

  /**
   * 两个线程循环打印 直到10
   */
  private static void test1() throws InterruptedException {
    Runnable runnable = () -> {
      int i = 0;
      synchronized (object) {
        while (i < 10) {
          System.out.println(Thread.currentThread().getName() + "  " + ++i);
          try {
            // 唤醒其他线程去竞争 但是该线程还持有锁 其他线程无法进入同步块
            object.notify();
            // 释放锁 其他线程能够进入同步块
            object.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };

    Thread t1 = new Thread(runnable);
    Thread t2 = new Thread(runnable);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
  }


  public static void main(String[] args) throws InterruptedException {
    test1();
  }
}
