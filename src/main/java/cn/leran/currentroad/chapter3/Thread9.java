package cn.leran.currentroad.chapter3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
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
  ConcurrentSkipListMap map2 = new ConcurrentSkipListMap();
}
