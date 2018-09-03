package cn.leran.currentroad.chapter2;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 不要通过thread.stop结束线程 通过自动方法结束
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread2 {

  public static class User {

    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public User(String name) {
      this.name = name;
    }
  }

  public static User u = new User("  a");

  public static class ReadThread extends Thread {

    @Override
    public void run() {
      while (true) {
        synchronized (u) {
          System.out.println(u.getName());
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
        synchronized (u) {
          DateFormat format = new SimpleDateFormat("HH:mm:ss");
          Date date = new Date();
          //如果时分秒有60
          if (StringUtils.contains(format.format(date), "00")) {
            System.out.println("修改线程停止了");
            stopThread();
          }
          u.setName(format.format(date) + "  a");
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
