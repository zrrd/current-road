package cn.leran.currentroad.chapter1;

/**
 * 守护线程 Daemon
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread7 {

  public static class DaemonT extends Thread {

    @Override
    public void run() {
      while (true) {
        System.out.println("i m alive");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * <pre>
   * 例如 main 函数为用户线程 垃圾回收为守护线程
   * 当最后一个守护线程结束后 jvm会正常退出 反之 只要有一个用户线程存在jvm就不会退出
   * </pre>
   */
  public static void main(String[] args) {
    Thread t = new DaemonT();
    // 将线程设置为守护线程，随着主线程的结束而结束
    // 注释后变成用户线程 在主线程结束后会一直打印 i m alive
    //t.setDaemon(true);
    t.start();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("main is over");
  }
}
