package cn.leran.currentroad.chapter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * AtomicInteger 通过cas技术,实现一个线程安全的int类型. 无锁策略
 *
 * @author 邵益炯
 * @date 2018/9/6
 */
public class Thread16 {

  /**
   * 除了AtomicInteger java.util.concurrent.atomic 下还有各种类型的数据类型
   * 高并发下 LongAdder DoubleAdder 优于 AtomicLong
   */
  private static AtomicInteger atomicInteger = new AtomicInteger(0);
  /**
   * 初始值为0.
   */
  private static LongAdder longAdder = new LongAdder();

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
    System.out.println(longAdder.toString());
    System.out.println(a);
  }
}
