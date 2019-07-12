package cn.leran.currentroad.chapter1;

/**
 * 启动一个线程
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread1 {

  /**
   * 一个简单的线程
   */
  public static void main(String[] args) {
    //通过lambda表达式实现一个runnable接口
    Runnable runnable = () -> System.out.println("线程开始了");
    Thread thread = new Thread(runnable);
    thread.start();
  }
}