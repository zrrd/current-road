package cn.leran.currentroad.chapter2;

import java.util.Date;
import java.util.concurrent.locks.LockSupport;
import org.junit.jupiter.api.Test;

/**
 * <pre>
 * LockSupport中主要是park和unpark方法以及设置和读取parkBlocker方法。
 * 所有的park 都可以加上 blocker 来标识是哪个线程被阻塞了 用于排除问题
 * </pre>
 *
 * @author shaoyijiong
 * @date 2019/8/27
 */
class Thread17 {


  /**
   * <pre>
   * begin park!
   * 在当前线程调用park时 会阻塞  知道其他线程调用 unpark(thread)
   * </pre>
   */
  @Test
  void test1() {

    System.out.println("begin park!");
    LockSupport.park();
    System.out.println("end park");
  }

  /**
   * <pre>
   * begin park!
   * end park
   * 线程被park 阻塞后 调用unpark会被唤醒
   * 线程先调用unpark 在park阻塞线程 会立刻返回 不阻塞线程
   * </pre>
   */
  @Test
  void test2() {
    System.out.println("begin park!");
    LockSupport.unpark(Thread.currentThread());
    LockSupport.park();
    System.out.println("end park");
  }

  @Test
  void test3() throws InterruptedException {
    Thread thread = new Thread(() -> {
      System.out.println("thread begin park!");
      LockSupport.park();
      System.out.println("thread end park");
    });
    thread.start();
    Thread.sleep(1000);
    LockSupport.unpark(thread);
  }

  /**
   * 阻塞指定时间
   */
  @Test
  void test4() {
    System.out.println("begin park!");
    LockSupport.parkNanos(1000);
    System.out.println("end park");
  }

  /**
   * 阻塞到指定时间
   */
  @Test
  void test5() {
    System.out.println("begin park!");
    LockSupport.parkUntil(new Date(System.currentTimeMillis() + 1000).getTime());
    System.out.println("end park");
  }

}
