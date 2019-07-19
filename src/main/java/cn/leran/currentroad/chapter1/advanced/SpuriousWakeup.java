package cn.leran.currentroad.chapter1.advanced;

import java.util.ArrayDeque;
import java.util.Queue;
import org.apache.commons.lang3.RandomUtils;

/**
 * 线程的虚假唤醒
 *
 * @author shaoyijiong
 * @date 2019/7/19
 */
public class SpuriousWakeup {

  private static final int max_size = 10;
  private static final Queue<String> queue = new ArrayDeque<>(max_size);

  private static void producer() {
    synchronized (queue) {
      // 当队列中元素慢了 释放queue上的锁 通知消费者消费
      // 这里使用while 而不是 if 防止被虚假唤醒
      // 当线程被虚假唤醒了  队列还是满的 把自己挂起 而不是再往队列中添加元素
      while (queue.size() == max_size) {
        try {
          System.out.println("队列慢了 生产者挂起");
          queue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      String s = String.valueOf(RandomUtils.nextInt());
      queue.add(s);
      System.out.println("生产者生产了" + s);
      queue.notifyAll();
    }
  }

  private static void consumer() {
    synchronized (queue) {
      // 当队列中元素慢了 释放queue上的锁 通知消费者消费
      // 这里使用while 而不是 if 防止被虚假唤醒
      // 当线程被虚假唤醒了  队列还是空 把自己挂起 而不是再往队列中拿元素
      while (queue.size() == 0) {
        try {
          System.out.println("队列为空 消费者挂起");
          queue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      String s = queue.remove();
      System.out.println("消费者消费了" + s);
      queue.notifyAll();
    }
  }


  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      while (true) {
        producer();
      }
    });

    Thread t2 = new Thread(() -> {
      while (true) {
        consumer();
      }
    });

    t1.start();
    t2.start();

    Thread.sleep(100000);
  }

}
