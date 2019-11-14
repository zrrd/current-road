package cn.leran.currentroad.chapter3.design;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * 等待环境机制规范实现
 *
 * @author shaoyijiong
 * @date 2019/11/14
 */
public class GuardedSuspensionGo {

  /**
   * <pre>
   *  场景: 有两个服务,服务A与服务B
   *  服务A通过MQ通知服务B业务处理,服务A等待服务B处理完毕
   *  上述场景规范实现
   *  两个线程之间的交互
   * </pre>
   */


  static class GuardedObject<T> {

    //受保护的对象
    T obj;
    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();
    final int timeout = 1;

    //获取受保护对象
    T get(Predicate<T> p) {
      lock.lock();
      try {
        //MESA管程推荐写法
        while (!p.test(obj)) {
          done.await(timeout, TimeUnit.SECONDS);
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } finally {
        lock.unlock();
      }
      //返回非空的受保护对象
      return obj;
    }

    //事件通知方法
    void onChanged(T obj) {
      lock.lock();
      try {
        this.obj = obj;
        done.signalAll();
      } finally {
        lock.unlock();
      }
    }
  }
}
