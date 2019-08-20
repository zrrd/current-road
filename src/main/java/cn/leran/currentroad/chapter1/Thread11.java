package cn.leran.currentroad.chapter1;

/**
 * @author shaoyijiong
 * @date 2019/8/20
 */
public class Thread11 {

  private static volatile int t = 0;
  private static int p = 0;


  public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new Thread(() -> {
      while (t <= 1000000) {
        t++;
        p = t;
      }
    });
    Thread thread2 = new Thread(() -> {
      while (t <= 1000000) {
        System.out.println("t:" + t);
        System.out.println("p:" + p);
      }
    });
    thread2.start();
    thread1.start();
    thread2.join();
    thread1.join();
  }


}
