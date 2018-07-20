package cn.leran.currentroad.chapter5;

/**
 * 线程安全的单例.
 *
 * @author shaoyijiong
 * @date 2018/7/19
 */
public class Singleton {

  /**
   * 将单例的构造函数设置private,外部不能访问.
   */
  private Singleton() {
    System.out.println("Singleton被创建");
  }

  /**
   * 通过内部类的方式创建单例.线程安全且,延迟加载.
   */
  private static class SingletonHolder {
    private static Singleton instance = new Singleton();
  }

  public static Singleton getInstance() {
    return SingletonHolder.instance;
  }
}
