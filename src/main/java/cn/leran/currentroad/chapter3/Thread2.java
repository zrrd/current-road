package cn.leran.currentroad.chapter3;

/**
 * 哲学家问题,导致死锁.
 *
 * @author shaoyijiong
 * @date 2018/7/18
 */
public class Thread2 extends Thread {

  private String happyName;

  static final Object fork1 = new Object();
  static final Object fork2 = new Object();

  public Thread2(String happyName) {
    this.happyName = happyName;
  }

  @Override
  public void run() {
    if ("哲学家1".equals(happyName)) {
      synchronized (fork1) {
        System.out.println("持有叉子一");
      }
      synchronized (fork2) {
        System.out.println("持有叉子二");
        try {
          System.out.println("吃饭");
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } else {
      if ("哲学家2".equals(happyName)) {
        synchronized (fork2) {
          System.out.println("持有叉子二");
        }
        synchronized (fork1) {
          System.out.println("持有叉子一");
          try {
            System.out.println("吃饭");
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    Thread2 t1 = new Thread2("哲学家1");
    Thread2 t2 = new Thread2("哲学家2");
    //多次循环发生死锁
    for (int i = 0; i < 1000; i++) {
      t1.start();
      t2.start();
    }
    System.out.println("没有发生死锁!");
  }

}
