package cn.leran.currentroad.chapter2.advanced;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 读写缓存数组
 *
 * @author shaoyijiong
 * @date 2019/9/3
 */
public class BoundedBuffer {

  private Object[] buffer = new Object[10];
  private ReentrantLock lock = new ReentrantLock();
  private final Condition notFull = lock.newCondition();
  private final Condition notEmpty = lock.newCondition();
  private int putptr, takeptr, count;

  public void put(Object x) throws InterruptedException {

    lock.lock();
    try {
      // 数组满了等待
      while (count == buffer.length) {
        notFull.await();
      }
      buffer[putptr] = x;
      if (++putptr == buffer.length) {
        putptr = 0;
      }
      ++count;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }

  }

  public Object take() throws InterruptedException {
    lock.lock();
    try {
      // 数组空了 等待
      while (count == 0) {
        notEmpty.await();
      }
      Object x = buffer[takeptr];
      if (++takeptr == buffer.length) {
        takeptr = 0;
      }
      --count;
      notFull.signal();
      return x;
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    // 必须先持有锁再调用 Condition 方法 否则在释放锁的时候会判断是否为当前线程持有锁,不是的话会报错
    // signal 也会判断是否是当前线程持有锁
    BoundedBuffer boundedBuffer = new BoundedBuffer();
    boundedBuffer.take();
  }
}
