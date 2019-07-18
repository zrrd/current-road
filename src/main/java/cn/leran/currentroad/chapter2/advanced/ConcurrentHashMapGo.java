package cn.leran.currentroad.chapter2.advanced;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang3.RandomUtils;

/**
 * 理解 currentHashMap
 *
 * @author shaoyijiong
 * @date 2019/7/18
 */
public class ConcurrentHashMapGo {

  /**
   * ConcurrentHashMap 的size 会延迟真正的size
   */
  private static void sizeTest() {
    // 用于计算map中的数量
    ConcurrentHashMap<Integer, Object> map = new ConcurrentHashMap<>();
    // 起10个线程
    ExecutorService service = Executors.newFixedThreadPool(100);

    Lock lock = new ReentrantLock();

    for (int i = 0; i < 100; i++) {
      service.submit(() -> {
        map.put(RandomUtils.nextInt(), new Object());
        System.out
            .printf("当前线程:%s ,  map.size()数量:%s\r\n", Thread.currentThread().getName(), map.size());
      });
    }
  }

  public static void main(String[] args) {
    sizeTest();
  }
}
