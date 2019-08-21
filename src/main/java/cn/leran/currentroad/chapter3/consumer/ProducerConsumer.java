package cn.leran.currentroad.chapter3.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生产者消费者模式的实现.
 *
 * @author shaoyijiong
 * @date 2018/7/19
 */
public class ProducerConsumer {

  /**
   * 阻塞队列,写入缓存最多1000.
   */
  private static BlockingQueue<String> queue = new LinkedBlockingDeque<>(1000);

  /**
   * 生产者.
   */
  public static class Producer implements Runnable {

    /**
     * 当前线程的随机数,用于生成随机.
     */
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    /**
     * 阻塞队列,用于存放产品和消费产品(和生产者的阻塞队列必须为同一个).
     */
    private BlockingQueue<String> queue;

    Producer(BlockingQueue<String> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      while (true) {
        try {
          System.out.println(Thread.currentThread().getName() + "正在制作产品");
          Thread.sleep(3000);
          //塞
          queue.put("-------产品" + Thread.currentThread().getName() + "----" + random.nextInt(100));
          System.out.println("产品放到队列中去了");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 消费者.
   */
  public static class Consumer implements Runnable {

    private BlockingQueue<String> queue;

    /**
     * 阻塞队列,用于存放产品和消费产品(和消费者的阻塞队列必须为同一个).
     */
    Consumer(BlockingQueue<String> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      while (true) {
        try {
          //拿  消费者的消费速度大于生产者的生产速度 会导致从队列中的拿这个方法阻塞当前线程
          String product = queue.take();
          System.out.println(Thread.currentThread() + "正在消费产品" + product);
          Thread.sleep(1500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 测试.
   */
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(new Consumer(queue));
    executorService.execute(new Consumer(queue));
    executorService.execute(new Consumer(queue));
    executorService.execute(new Producer(queue));
    executorService.execute(new Producer(queue));
    executorService.execute(new Producer(queue));
    executorService.shutdown();
  }

}
