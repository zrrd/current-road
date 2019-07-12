package cn.leran.currentroad.chapter1;

import org.springframework.util.StopWatch;


/**
 * 线程的优先级.
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread8 {

  private static final int MIN_PRIORITY = 1;
  private static final int NORM_PRIORITY = 5;
  private static final int MAX_PRIORITY = 10;

  public static class LowPriority extends Thread {

    static int count = 0;

    @Override
    public void run() {
      StopWatch sw = new StopWatch();
      sw.start("low");
      while (true) {
        synchronized (Thread8.class) {
          count++;
          if (count > 10000000) {
            System.out.println("LowPriority is complete");
            sw.stop();
            System.out.println("耗时：" + sw.getLastTaskTimeMillis());
            break;
          }
        }
      }
    }
  }

  public static class HighPriority extends Thread {

    static int count = 0;

    @Override
    public void run() {
      StopWatch sw = new StopWatch();
      sw.start("high");
      while (true) {
        synchronized (Thread8.class) {
          count++;
          if (count > 10000000) {
            System.out.println("HighPriority is complete");
            sw.stop();
            System.out.println("耗时：" + sw.getLastTaskTimeMillis());
            break;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    Thread low = new LowPriority();
    Thread high = new HighPriority();
    low.setPriority(MIN_PRIORITY);
    high.setPriority(MAX_PRIORITY);
    low.start();
    high.start();
  }
}
