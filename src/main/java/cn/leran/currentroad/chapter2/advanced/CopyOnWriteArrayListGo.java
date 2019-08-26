package cn.leran.currentroad.chapter2.advanced;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author shaoyijiong
 * @date 2019/8/26
 */
public class CopyOnWriteArrayListGo {

  /**
   * <pre>
   * 在增删改的时候加锁 , 并且先修改副本 , 再将array的引用指向修改后的副本 , 后解锁
   * get 和 size 没有加锁操作 , 直接获取当前的引用的值 , 当另一个线程改了副本 , 并将array引用指向副本
   * get 返回的值还是老的值 存在弱一致性
   * </pre>
   */
  static CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

  /**
   * 通过 CopyOnWriteArrayList 的迭代器 显示 copyOnWriteArrayList 的弱一致性
   */
  public static void main(String[] args) {
    copyOnWriteArrayList.add("a");
    Iterator<String> iterator = copyOnWriteArrayList.iterator();
    copyOnWriteArrayList.add("b");
    copyOnWriteArrayList.add("c");
    // 只会打印a
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }
}
