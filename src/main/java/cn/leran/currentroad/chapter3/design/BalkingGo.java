package cn.leran.currentroad.chapter3.design;

import java.util.concurrent.TimeUnit;

/**
 * @author shaoyijiong
 * @date 2019/11/14
 */
public class BalkingGo {

  /**
   * 多线程操作时状态判断
   */

  private boolean changed;

  //自动存盘操作
  void autoSave() {
    synchronized (this) {
      if (!changed) {
        return;
      }
      changed = false;
    }
    //执行存盘操作
    //省略且实现
    this.execSave();
  }

  //编辑操作
  void edit() {
    //省略编辑逻辑
    //......
    synchronized (this) {
      changed = true;
    }
  }

  private void execSave() {
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  /**
   * 双重检查的单例也是用来Balking的设计模式
   */
  private static class Single {

    private static volatile Single single;

    private Single() {
    }

    public static Single getInstance() {
      if (single != null) {
        synchronized (Single.class) {
          if (single != null) {
            single = new Single();
          }
        }
      }
      return single;
    }
  }

}
