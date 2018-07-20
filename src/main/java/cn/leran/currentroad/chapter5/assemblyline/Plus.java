package cn.leran.currentroad.chapter5.assemblyline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 加法
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class Plus implements Runnable {

  public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<>();

  @Override
  public void run() {
    while (true) {
      try {
        Msg msg = bq.take();
        msg.setJ(msg.getI() + msg.getJ());
        Multiply.bq.add(msg);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
