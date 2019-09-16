package cn.leran.currentroad.chapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 使用示例
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread4 {


  /**
   * 将一个线程不安全的 列表 通过加锁的方式改成一个线程安全的列表
   */
  static class SynchronizedList {

    List<String> list;
    /**
     * 构造一个非公平读写锁
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    private Lock readLock = lock.readLock();
    /**
     * 写锁
     */
    private Lock writeLock = lock.writeLock();


    public SynchronizedList() {
      this.list = new ArrayList<>();
    }

    public void add(String str) {
      writeLock.lock();
      try {
        list.add(str);
      } finally {
        writeLock.unlock();
      }
    }

    public void remove(String str) {
      writeLock.lock();
      try {
        list.remove(str);
      } finally {
        writeLock.unlock();
      }
    }

    public String get(int index) {
      readLock.lock();
      try {
        return list.get(index);
      } finally {
        readLock.unlock();
      }
    }

    public int size() {
      readLock.lock();
      try {
        return list.size();
      } finally {
        readLock.unlock();
      }
    }
  }


  public static void main(String[] args) throws InterruptedException {
    SynchronizedList list = new SynchronizedList();
    //List<String> list = new ArrayList<>();
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        list.add(String.valueOf(i));
      }
    });

    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        list.add(String.valueOf(i));
      }
    });

    Thread t3 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        System.out.println(list.get(list.size() - 1));
      }
    });

    Thread t4 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        System.out.println("size " + list.size());
      }
    });

    t1.start();
    t2.start();
    t3.start();
    t4.start();

    t1.join();
    t2.join();
    t3.join();
    t4.join();

    System.out.println("final size " + list.size());
  }
}
