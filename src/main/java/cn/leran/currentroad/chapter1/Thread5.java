package cn.leran.currentroad.chapter1;

/**
 * join 和 yield Thread.join() 线程的等待 和 Thread.yield()让出当前cpu
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread5 {

  private static volatile int i = 0;

  public static class ThreadOne extends Thread {

    @Override
    public void run() {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("thread one 执行结束");
    }

  }

  public static class ThreadTwo extends Thread {

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("thread tow 执行结束");
    }
  }


  public static void main(String[] args) throws InterruptedException {
    ThreadOne threadOne = new ThreadOne();
    ThreadTwo threadTwo = new ThreadTwo();
    threadOne.start();
    threadTwo.start();



    //主线程等待addThread线程  本质是通过wait()当前线程对象实例 线程退出前进行 notifAll()操作

    // 阻塞当前线程 等待线程1执行完
    threadOne.join();
    // 阻塞当前线程 等待线程2执行完
    threadTwo.join();
  }
}
