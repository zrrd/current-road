package cn.leran.currentroad.chapter3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * CountDownLatch实现多线程直接的通信. 例子:执行多个线程,统计结果,等待所有线程结束才能统计 CountDownLatch是一次性的，计数器的值只能在构造方法中初始化一次
 * 模拟收集七龙珠
 *
 * @author 邵益炯
 * @date 2018/9/3
 */
public class Thread11 {

  private static final int THREAD_NUM = 7;

  private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

  static class RunnableA implements Runnable {

    private int index;


    RunnableA(int index) {
      this.index = index;
    }

    @Override
    public void run() {
      ThreadLocalRandom random = ThreadLocalRandom.current();
      int num = random.nextInt(10);
      try {
        System.out.println("开始找龙珠" + index);
        //模拟收集龙珠的时间
        Thread.sleep(1000 * num);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        System.out.println("花了" + num + "秒找到龙珠" + index);
        //表示当前线程已经执行完了
        countDownLatch.countDown();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM);
    for (int i = 1; i <= THREAD_NUM; i++) {
      pool.submit(new RunnableA(i));
    }
    //等待所有线程执行完
    countDownLatch.await();
    System.out.println("集齐7课龙珠,召唤神龙");
  }
}
