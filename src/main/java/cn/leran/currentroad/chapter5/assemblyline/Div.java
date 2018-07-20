package cn.leran.currentroad.chapter5.assemblyline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 除法.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class Div implements Runnable {

  public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<>();

  @Override
  public void run() {
    while (true) {
      try {
        Msg msg = bq.take();
        msg.setI(msg.getI() / 2);
        System.out.println(msg.getOrgStr() + "=" + msg.getI());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
