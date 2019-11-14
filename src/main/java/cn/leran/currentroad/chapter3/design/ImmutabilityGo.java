package cn.leran.currentroad.chapter3.design;

/**
 * 使用不变性解决并发问题
 *
 * @author shaoyijiong
 * @date 2019/11/14
 */
public class ImmutabilityGo {

  /**
   * <pre>
   *  如何实现一个不可变的类
   *  1.将一个类所有的属性都设置成 final 的
   *  2.方法只可读
   *  3.推荐类本身是final的 不允许继承
   *  ---
   *  不可变类可能会导致创建大量对象,可以用享元模式优化
   *  java中[-128,127]的数字会被缓存,也是通过享元模式的优化
   * </pre>
   */
  private static final class Immutability {

    private final String a;
    private final String b;

    private Immutability(String a, String b) {
      this.a = a;
      this.b = b;
    }

    public String getA() {
      return a;
    }

    public String getB() {
      return b;
    }
  }
}
