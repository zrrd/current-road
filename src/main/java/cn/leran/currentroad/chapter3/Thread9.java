package cn.leran.currentroad.chapter3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Java并发工具讲解(都是线程安全的).
 *
 * @author shaoyijiong
 * @date 2018/7/18
 */
public class Thread9 {

  /**
   * 线程安全的 map 和 list,效率较低,不如下面的工具.
   */
  Map map1 = Collections.synchronizedMap(new HashMap<>());
  List list1 = Collections.synchronizedList(new ArrayList<>());

  ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

  /**
   * 高效的读写队列 ConcurrentLinkedDeque, 多消费者的场景.
   */
  ConcurrentLinkedDeque queue1 = new ConcurrentLinkedDeque();

  /**
   * 高效读取,读取远远大于写入的场景.
   */
  CopyOnWriteArrayList list2 = new CopyOnWriteArrayList();

  /**
   * 数据共享通道,单消费者的场景.
   */
  LinkedBlockingDeque queue2 = new LinkedBlockingDeque();
  ArrayBlockingQueue queue3 = new ArrayBlockingQueue(10);
  /**
   * 跳表,快速查找.
   */
  ConcurrentSkipListMap<String, String> map2 = new ConcurrentSkipListMap();

  public static void main(String[] args) {
    Thread9 thread9 = new Thread9();
    ConcurrentHashMap<String, String> map = thread9.concurrentHashMap;
    //新方法 putIfAbsent() 只在提供的键不存在时，将新的值添加到映射中
    map.putIfAbsent("a", "a");
    //方法返回指定键的值。在传入的键不存在时，会返回默认值
    map.getOrDefault("b", "hello");
    //许我们转换单个元素，而不是替换映射中的所有值。这个方法接受
    //需要处理的键，和用于指定值的转换的 BiFunction
    map.compute("foo", (key, value) -> value + value);
    // merge() 方法可以用于以映射中的现有值来统一新的值。这个方法接受
    //键、需要并入现有元素的新值，以及指定两个值的合并行为的 BiFunction
    map.merge("foo", "boo", (oldVal, newVal) -> newVal + " was " + oldVal);
    map.put("a", "a");
  }
}
