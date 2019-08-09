package cn.leran.currentroad.chapter3;

/**
 * <pre>
 *   ThreadLocal 无法获取父线程
 * </pre>
 *
 *
 * @author shaoyijiong
 * @date 2019/8/8
 */
public class Thread4 {

  private static final ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
  private static final ThreadLocal<String> threadLocal2 = new InheritableThreadLocal<>();


  public static void main(String[] args) throws InterruptedException {
    threadLocal1.set("hello");
    threadLocal2.set("world");

    System.out.println("父线程");
    System.out.println(threadLocal1.get());
    System.out.println(threadLocal2.get());

    Runnable runnable = () -> {
      System.out.println("子线程");
      System.out.println(threadLocal1.get());
      System.out.println(threadLocal2.get());
    };

    Thread thread = new Thread(runnable);
    thread.start();
    thread.join();
  }
}
