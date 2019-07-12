package cn.leran.currentroad.chapter1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 不要通过thread.stop结束线程 通过自动方法结束
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread2 {

  @Data
  public static class User {

    private String name;

    User(String name) {
      this.name = name;
    }
  }

  /**
   * 用来做线程控制的量
   */
  private static final User U = new User("a");

  public static class ReadThread extends Thread {

    @Override
    public void run() {
      while (true) {
        synchronized (U) {
          System.out.println(U.getName());
          Thread.yield();
        }
      }
    }
  }

  public static class ChangeThread extends Thread {

    /**
     * 退出线程的标志
     */
    private boolean isRun = true;

    private void stopThread() {
      isRun = false;
    }

    @Override
    public void run() {
      while (isRun) {
        synchronized (U) {
          DateFormat format = new SimpleDateFormat("HH:mm:ss");
          Date date = new Date();
          //如果时分秒有60
          if (StringUtils.contains(format.format(date), "00")) {
            System.out.println("修改线程停止了");
            stopThread();
          }
          U.setName(format.format(date) + "  a");
          Thread.yield();
        }
      }
    }
  }

  public static void main(String[] args) {
    new ReadThread().start();
    new ChangeThread().start();
  }
}
