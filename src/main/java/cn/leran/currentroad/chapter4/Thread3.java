package cn.leran.currentroad.chapter4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger 通过cas技术,实现一个线程安全的int类型. 无锁策略
 *
 * @author 邵益炯
 * @date 2018/9/6
 */
public class Thread3 {

  /**
   * 除了AtomicInteger java.util.concurrent.atomic 下还有各种类型的数据类型
   * 高并发下 LongAdder 优于 AtomicLong
   */
  private static AtomicInteger atomicInteger = new AtomicInteger(0);
  private static Integer a = 0;

  /**
   * 测试.
   */
  public static void main(String[] args) {
    Runnable r = () -> {
      atomicInteger.addAndGet(1);
      a = a + 1;
      System.out.println("atomicInteger" + atomicInteger.get());
      System.out.println("a:" + a);
    };
    ExecutorService pool = Executors.newFixedThreadPool(10000);
    for (int i = 0; i < 10000; i++) {
      pool.submit(r);
    }
    //等待所有任务结束  AtomicInteger = 1000  Integer < 1000
    pool.shutdown();
    System.out.println(atomicInteger.get());
    System.out.println(a);
  }
}
