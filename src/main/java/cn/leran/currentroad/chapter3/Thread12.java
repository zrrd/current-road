package cn.leran.currentroad.chapter3;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 通过CyclicBarrier来实现碰碰车等待
 *
 * @author 邵益炯
 * @date 2018/9/3
 */
public class Thread12 {

  /**
   * 模拟碰碰车数量为10.
   */
  private static final int CAR_NUM = 10;

  /**
   * 传入一个runnable参数,每次await后运行
   */
  private static CyclicBarrier cyclicBarrier = new CyclicBarrier(CAR_NUM, () -> {
    try {
      System.out.println("碰碰车正在开");
      Thread.sleep(5000);
      System.out.println("碰碰车开完了");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  });

  static class WaitingForCar implements Runnable {

    String carName;

    public WaitingForCar(int num) {
      this.carName = "碰碰车" + num;
    }

    @Override
    public void run() {
      ThreadLocalRandom random = ThreadLocalRandom.current();
      try {
        while (true) {
          System.out.println("等待用户坐上" + carName);
          int num = random.nextInt(10);
          //模拟碰碰车等待客户
          Thread.sleep(num * 1000);
          System.out.println("等待" + num + "秒后," + carName + "有客户");
          cyclicBarrier.await();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(CAR_NUM);
    for (int i = 1; i < CAR_NUM + 1; i++) {
      pool.submit(new WaitingForCar(i));
    }
  }

}
