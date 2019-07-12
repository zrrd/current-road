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

  public static void main(String[] args) {
    Thread t = new DaemonT();
    //将线程设置为守护线程，随着主线程的结束而结束
    t.setDaemon(true);
    t.start();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
