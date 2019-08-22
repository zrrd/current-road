package cn.leran.currentroad.chapter2.advanced;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author shaoyijiong
 * @date 2019/8/22
 */
public class AtomicStampedReferenceGo {

  /**
   * 初始化的两个参数  initialRef  初始化引用 initialStamp 初始化的版本戳
   */
  private static AtomicStampedReference<String> i = new AtomicStampedReference<>("a", 1);

  /**
   * 测试代码
   */
  public static void main(String[] args) {
    //如果当前引用 等于 预期值并且 当前版本戳等于预期版本戳, 将更新新的引用和新的版本戳到内存
    boolean success1 = i.attemptStamp("a", 2);
    System.out.println(success1 + " " + i.getReference() + " " + i.getStamp());
    // 设置新的引用和版本戳
    i.set("b", i.getStamp() + 1);
    // expectedReference 原来的预期值  newReference 更新值  expectedStamp 预期版本戳 newStamp 新版本戳
    boolean success2 = i.compareAndSet("b", "c", i.getStamp(), i.getStamp() + 1);
    System.out.println(success2);
  }
}
