package cn.leran.currentroad.chapter5;

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

  public static class Producer implements Runnable {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    private BlockingQueue<String> queue;

    public Producer(BlockingQueue queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      while (true) {
        try {
          System.out.println(Thread.currentThread().getName() + "正在制作产品");
          Thread.sleep(3000);
          queue.put("-------产品" + Thread.currentThread().getName() + "----" + random.nextInt(100));
          System.out.println("产品放到队列中去了");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static class Consumer implements Runnable {

    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      while (true) {
        try {
          String product = queue.take();
          System.out.println(Thread.currentThread() + "正在消费产品" + product);
          Thread.sleep(1500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

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
