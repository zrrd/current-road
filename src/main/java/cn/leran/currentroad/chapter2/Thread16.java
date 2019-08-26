package cn.leran.currentroad.chapter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * AtomicInteger 通过cas技术,实现一个线程安全的int类型. 无锁策略
 *
 * @author 邵益炯
 * @date 2018/9/6
 */
public class Thread16 {

  /**
   * <pre>
   * 除了AtomicInteger java.util.concurrent.atomic 下还有各种类型的数据类型
   * 基于 unsafe 类 , 实现cas操作 , 在高并发下多个线程竞争 , 存在问题
   * </pre>
   */
  private static AtomicInteger atomicInteger = new AtomicInteger(0);
  /**
   * <pre>
   * 初始值为0. 高并发下 LongAdder DoubleAdder 优于 AtomicLong
   * 高并发下 , 只有一个线程能够cas成功 , 其他线程会无限循环不断进行自旋尝试cas操作 , 白白浪费cpu资源
   * LongAdder 通过将变量分为多个 , 让多线程竞争多个资源 Cell[] as,解决性能问题
   * cell 里面有一个初始化为0的long型变量,当前Cell cas失败后 , 去其他变量上进行cas操作 , 获取LongAdder值时 , 累加所有的 cell 加上 base 返回的
   * LongAdder 维护了一个延迟初始化Cell[] 数组 , cell的数组大小为 2的N次方
   * </pre>
   */
  private static LongAdder longAdder = new LongAdder();
  /**
   * <pre>
   *  LongAdder 是一种特殊的 LongAccumulator
   *  LongAdder 的操作 x+y 而 LongAccumulator 自定义操作
   * </pre>
   */
  private static LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x * y, 0);

  private static Integer a = 0;

  /**
   * 测试.
   */
  public static void main(String[] args) throws InterruptedException {
    Runnable r = () -> {
      //原子加一
      atomicInteger.getAndIncrement();
      //atomicInteger.addAndGet(2); 增加一个固定的值
      //atomicInteger.updateAndGet(n -> n + 2); 通过lambda表达式设值
      //等同  longAdder.increment();
      longAdder.add(1);

      a = a + 1;
    };
    ExecutorService pool = Executors.newFixedThreadPool(10000);
    for (int i = 0; i < 10000; i++) {
      pool.submit(r);
    }
    //等待2s所有任务结束  AtomicInteger = 1000  Integer < 1000
    pool.shutdown();
    Thread.sleep(2000);

    System.out.println(atomicInteger.get());
    // sum 时没有加锁 返回的值并不是非常精确
    System.out.println(longAdder.sum());
    System.out.println(a);
  }
}
