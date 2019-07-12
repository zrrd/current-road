package cn.leran.currentroad.chapter3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal的简单使用.
 *
 * @author shaoyijiong
 * @date 2018/7/18
 */
public class Thread1 {

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  /**
   * 通过ThreadLocal,为每个线程分配一个SimpleDateFormat.
   */
  private static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<>();

  public static class ParseDate implements Runnable {

    int ss;

    ParseDate(int i) {
      this.ss = i;
    }

    @Override
    public void run() {
      try {
        //sdf.parse 是线程不安全的 多线程共同使用会导致错误
        //Date date = sdf.parse("2018-03-29 19:29:" + ss % 60);

        //如果当前线程没有 SimpleDateFormat 对象新建一个到线程中去
        if (t1.get() == null) {
          t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }
        Date date = t1.get().parse("2018-03-29 19:29:" + ss % 60);
        t1.remove();
        System.out.println(ss + ":" + date);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 1000; i++) {
      es.execute(new ParseDate(i));
    }
    t1.remove();
  }
}
