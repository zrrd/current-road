package cn.leran.currentroad.chapter3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Java并发工具讲解(都是线程安全的). 3种类型  Concurrent,CopyOnWrite,Blocking开头的
 *
 * @author shaoyijiong
 * @date 2018/7/18
 */
public class Thread9 {

  /**
   * 线程安全的 map 和 list,效率较低,不如下面的工具. 相当于给所有操作加上了synchronized的语义
   */
  Map synchronizedMap = Collections.synchronizedMap(new HashMap<>());
  List synchronizedList = Collections.synchronizedList(new ArrayList<>());

  /**
   * 线程安全的HashMap.
   */
  ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

  /**
   * 高效的读写队列 ConcurrentLinkedDeque不支持阻塞. (一般建议使用LinkedBlockingQueue,除非有特殊需求)
   */
  ConcurrentLinkedDeque<String> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();

  /**
   * 高效读取,读取远远大于写入的场景. 修改的时候直接copy数组  List哦
   */
  CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
  CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

  /**
   * 数据共享通道,单消费者的场景. 无界队列和有界队列 当然还有对应的Deque(双向队列) Queue只能在队尾插入 队头出.Deque在队头和队尾都能进出
   */
  LinkedBlockingQueue<String> linkedBlockingDeque = new LinkedBlockingQueue<>();
  ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
  /**
   * 跳表,快速查找且有序.
   */
  ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();

  /**
   * 容量为0点queue 先拿再放  放了以后直接消费. 它的性能表现往往大大超过其他实现,尤其在队列较小的场景.
   * 也是 blockingQueue大家族的一员
   */
  SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

  /**
   * 优先级队列(无界). 非线程安全的
   */
  PriorityQueue<String> priorityQueue = new PriorityQueue<>();

  /**
   * 无界的BlockingQueue 用于放置实现了Delayed接口的对象，其中的对象只能在其到期时才能从队列中取走.
   * blockingQueue 一员
   */
  DelayQueue delayQueue = new DelayQueue<>();


  /**
   * concurrentHashMap的使用.
   */
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
