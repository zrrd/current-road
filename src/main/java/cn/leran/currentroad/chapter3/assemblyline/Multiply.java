package cn.leran.currentroad.chapter3.assemblyline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 乘法.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class Multiply implements Runnable {

  public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<>();

  @Override
  public void run() {
    while (true) {
      try {
        Msg msg = bq.take();
        msg.setI(msg.getJ() * msg.getI());
        Div.bq.add(msg);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
