package cn.leran.currentroad.chapter1;

/**
 * interrupt 通过线程中断退出线程
 * <pre>
 * 具体来说，当对一个线程，调用 interrupt() 时，
 * ① 如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常。仅此而已。
 * ② 如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。被设置中断标志的线程将继续正常运行，不受影响。
 * </pre>
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread3 {

  private static final Object object = new Object();

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      Thread.currentThread().interrupt();
      while (true) {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println("interrupted" + "  线程中断了啊！");
          break;
        }
        Thread.yield();
        System.out.println("线程进行中");
      }
    });

    Thread t2 = new Thread(() -> {
      try {
        Thread.sleep(100000);
      } catch (InterruptedException e) {
        System.out.println("在sleep时调用interrupt函数,抛出异常InterruptedException");
      }
    });

    Thread t3 = new Thread(() -> {
      synchronized (object) {
        try {
          object.wait();
        } catch (InterruptedException e) {
          // 抛出异常后 中断表示会自动清除
          System.out.println(Thread.currentThread().isInterrupted());
          System.out.println("在wait时调用interrupt函数,抛出异常InterruptedException");

          // 中不中断由自己决定，如果需要真真中断线程，则需要重新设置中断位，如果
          // 不需要，则不用调用
          Thread.currentThread().interrupt();
        }
      }
    });
    t1.start();
    Thread.sleep(2000);
    // 这边不是真的中断线程  而是通知线程该中断了  是否需要中断还是有线程本身决定
    // t1.stop 其他线程强制中断某个线程 被废弃了
    t1.interrupt();

    t1.join();

    //被废弃了 t1.stop();

    t2.start();
    t2.interrupt();
    t2.join();

    t3.start();
    t3.interrupt();
    t2.join();
  }
}
