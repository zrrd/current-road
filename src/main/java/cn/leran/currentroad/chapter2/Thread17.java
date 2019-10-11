package cn.leran.currentroad.chapter2;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author shaoyijiong
 * @date 2019/9/29
 */
public class Thread17 {

  private static LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(10);

  public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new Thread(() -> {
      while (true) {
        System.out.println("开始放数据");
        try {
          linkedBlockingQueue.put("a");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    Thread thread2 = new Thread(() -> {
      try {
        // 等待10s , 等放慢
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      while (true) {
        System.out.println("开始取数据");
        String poll = null;
        try {
          poll = linkedBlockingQueue.take();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        //System.out.println(poll);
      }
    });


    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

  }

}
