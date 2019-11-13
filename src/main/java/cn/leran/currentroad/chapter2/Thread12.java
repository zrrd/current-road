package cn.leran.currentroad.chapter2;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 通过CyclicBarrier来实现碰碰车等待.
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
   * <pre>
   * 传入一个runnable参数,每次await=0后运行. 注意的是Runnable并不会另起一个线程,判断是否要另起一个线程
   * 比如本例子中,碰碰车需要等待开完才能等待用户做,所有直接在主线程中调用
   * 再比如有两个厨师做菜,要等两个厨师都做完菜了才能上菜,其中做菜是屏障任务,上菜是回调函数,第一轮菜做完后,不需要等上菜上完再
   * 开始做第二轮菜,所以需要把上菜的任务放到另一个线程中去,异步话任务
   * </pre>
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

    WaitingForCar(int num) {
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
          //等待所有碰碰车上都有顾客
          cyclicBarrier.await();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 测试下.
   */
  public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(CAR_NUM);
    for (int i = 1; i < CAR_NUM + 1; i++) {
      pool.submit(new WaitingForCar(i));
    }
  }

}
