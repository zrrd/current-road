package cn.leran.currentroad.chapter1;

/**
 * interrupt 通过线程中断退出线程
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread3 {

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread() {
      @Override
      public void run() {
        while (true) {
          if (Thread.currentThread().isInterrupted()) {
            System.out.println("interrupted" + "  线程中断了啊！");
            break;
          }
          Thread.yield();
          System.out.println("线程进行中");
        }
      }
    };
    t1.start();
    Thread.sleep(2000);
    t1.interrupt();
  }
}
