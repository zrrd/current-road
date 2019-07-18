package cn.leran.currentroad.chapter1;

/**
 * <pre>
 * 调用了start() 方法后线程没有马上执行 而是处于就绪状态(该线程已经获取了除了CPU资源以外的其他资源)
 * 等待获取CPU资源后才会处于真正的运行状态
 * </pre>
 * 启动一个线程
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread1 {


  /**
   * 通过实现runnable接口开启线程
   */
  private void runnable() {
    //通过lambda表达式实现一个runnable接口
    Runnable runnable = () -> System.out.println("线程开始了");
    Thread thread = new Thread(runnable);
    thread.start();
  }


  static class MyThread extends Thread {

    @Override
    public void run() {
      System.out.println("线程开始了");
    }
  }

  private void thread() {
    new MyThread().start();
  }


  /**
   * 一个简单的线程
   */
  public static void main(String[] args) {

  }
}