package cn.leran.currentroad.chapter5.assemblyline;

/**
 * 实践类,通过流水线的方式,完成(I + J) * I / 2 的操作.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class GoGoGo {

  public static void main(String[] args) {
    new Thread(new Plus()).start();
    new Thread(new Multiply()).start();
    new Thread(new Div()).start();

    for (int i = 1; i < 1000; i++) {
      for (int j = 1; j < 1000; j++) {
        Msg msg = new Msg();
        msg.setI(i);
        msg.setJ(j);
        msg.setOrgStr("((" + i + "+" + j + ")*" + i + ")/2");
        Plus.bq.add(msg);
      }
    }
  }
}
