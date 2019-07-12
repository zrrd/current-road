package cn.leran.currentroad.chapter2;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock优化读写锁.
 *
 * @author 邵益炯
 * @date 2018/9/3
 */
public class Thread10 {

  private final StampedLock s1 = new StampedLock();
  private Integer testNum = 10;

  private void write() {
    testNum = testNum + 1;
  }

  private Integer read() {
    return testNum;
  }

  void mutate() {
    long stamp = s1.writeLock();
    try {
      write();
    } finally {
      s1.unlock(stamp);
    }
  }

  Integer access() {
    long stamp = s1.tryOptimisticRead();
    Integer data = read();
    if (!s1.validate(stamp)) {
      stamp = s1.readLock();
      try {
        data = read();
      } finally {
        s1.unlockRead(stamp);
      }
    }
    return data;
  }
}
