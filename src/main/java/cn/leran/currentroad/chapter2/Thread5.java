package cn.leran.currentroad.chapter2;

/**
 * join 和 yield Thread.join() 线程的等待 和 Thread.yield()让出当前cpu
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread5 {

  private static volatile int i = 0;

  public static class AddThread extends Thread {

    @Override
    public void run() {
      for (i = 0; i < 100000; i++) {
        System.out.println(i);
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    AddThread thread = new AddThread();
    thread.start();
    //主线程等待addThread线程  本质是通过wait()当前线程对象实例 线程退出前进行 notifAll()操作
    thread.join();
    System.out.println(i);
  }
}
