package cn.leran.currentroad.chapter3.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * 通过disruptor来实现生产者消费者模式,无锁的方式,效率更高.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class Consumer implements WorkHandler<PcData> {

  /**
   * 数据读取由disruptor进行封装,onEvent为框架的回调.
   *
   * @param event 回调的数据
   */
  @Override
  public void onEvent(PcData event) {
    System.out.println(
        Thread.currentThread().getName() + ":Event:--" + event.getValue()
            + "--");
  }
}
