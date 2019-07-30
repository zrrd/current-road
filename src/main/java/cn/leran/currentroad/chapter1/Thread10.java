package cn.leran.currentroad.chapter1;

/**
 * @author shaoyijiong
 * @date 2019/7/30
 */
public class Thread10 {

  public static void main(String[] args) throws InterruptedException {
    Runnable runnable = () -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      String name = Thread.currentThread().getName();
      System.out.println(name + "start");

      // 加上yield 后 start 与 cpu gat again 不是顺序出现
      //System.out.println(name + "yield cpu");
      //Thread.yield();

      System.out.println(name + "cpu gat again");
    };

    Thread t1 = new Thread(runnable);
    Thread t2 = new Thread(runnable);
    Thread t3 = new Thread(runnable);

    t1.start();
    t2.start();
    t3.start();

    t1.join();
    t2.join();
    t3.join();
  }
}
