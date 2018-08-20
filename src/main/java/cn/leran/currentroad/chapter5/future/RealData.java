package cn.leran.currentroad.chapter5.future;

import java.util.concurrent.Callable;

/**
 * 构造java内置Future的使用.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class RealData implements Callable<String> {

  private String para;

  RealData(String para) {
    this.para = para;
  }

  /**
   * 跟runnable的run很类似 多了一个返回
   *
   * @return 返回
   */
  @Override
  public String call() throws Exception {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 100; i++) {
      sb.append(para);
      //模拟处理时间
      Thread.sleep(100);
    }
    return sb.toString();
  }

}
