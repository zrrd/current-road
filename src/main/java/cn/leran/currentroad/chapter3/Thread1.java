package cn.leran.currentroad.chapter3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal的简单使用.
 * <p>ThreadLocal 适用于每个线程需要自己独立的实例且该实例需要在多个方法中被使用，也即变量在线程间隔离而在方法或类间共享的场景</p>
 * <a href="https://mp.weixin.qq.com/s?__biz=MzI4NDY5Mjc1Mg==&mid=2247488457&idx=2&sn=60f218dadb87b1daa88bdaff18f0e41a&chksm=ebf6cbb6dc8142a06f8651513eda1081acfc6a7ecbe91ffe2ec93a0c52342e0a493c03d2ae85&mpshare=1&scene=24&srcid=#rd">
 * ThreadLocal 详解</a>
 * <pre>
 * Thread 类内部持有一个有属性变量 threadLocals （类型是 ThreadLocal.ThreadLocalMap 可以理解为一个定制化的HashMap），
 * 也就是说每个线程有一个自己的 ThreadLocalMap ，所以每个线程往这个 ThreadLocal 中读写隔离的，并且是互相不会影响的。
 * 一个 ThreadLocal 只能存储一个 Object 对象，如果需要存储多个 Object 对象那么就需要多个 ThreadLocal！！
 * ThreadLocal 是不保存每个线程的值的 是由Thread对象保存 即上面的所说的ThreadLocalMap 保存
 * </pre>
 * <pre>
 * ThreadLocalMap 的key 是一个弱引用(弱引用也是用来描述非必需对象的，当 JVM 进行垃圾回收时，无论内存是否充足，该对象仅仅被弱引用关联，那么就会被回收)
 * 指向的是对应的ThreadLocal对象 如下面的t1 t2 , value 就是对应的值 如下面的 DateFormat String
 *
 * 当调用ThreadLocal的set方法时 , 会获取当前线程Thread类的实例 拿到该实例的ThreadLocalMap 对象(该对象的访问权限是 default ThreadLocal 与 Thread是同一个包所以能拿到)
 * key 为一个弱引用的ThreadLocal对象 value 就是要set的值(所以threadLocal对象需要唯一)
 * 当调用ThreadLocal的get方法时 , 同样拿到Thread的ThreadLocalMap 对象 , 通过本身为key  拿到对应的值
 *
 * 所以综上所述 ThreadLocal对象在整个流程中相当于一个key的角色 也相当于一个Thread类的工具类
 * </pre>
 *
 * <pre>
 * ThreadLocal 弱引用 详解 (针对threadLocal 定义为临时变量的情况)
 * 当仅仅只有 ThreadLocalMap 中的 Entry 的 key 指向 ThreadLocal 的时候，ThreadLocal 会进行回收的！！！
 * ThreadLocal 被垃圾回收后，在 ThreadLocalMap 里对应的 Entry 的键值会变成 null，但是 Entry 是强引用，那么 Entry 里面存储的 Object，并没有办法进行回收
 * ThreadLocal 进行了特殊处理 当后面再调用set get remove方法时 会将key为nul
 * </pre>
 *
 * @author shaoyijiong
 * @date 2018/7/18
 */
public class Thread1 {

  /**
   * 线程共享一个DateFormat
   */
  private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  /**
   * <pre>
   * 通常threadLocal对象被private static修饰符修饰
   * 通过ThreadLocal,为每个线程分配一个SimpleDateFormat. 多线程无法共享
   * 当ThreadLocal所属线程结束后 对应的ThreadLocal对象也会被回收
   * </pre>
   */
  private static ThreadLocal<DateFormat> t1 = new ThreadLocal<>();
  private static ThreadLocal<String> t2 = new ThreadLocal<>();

  /**
   * 线程不安全
   */
  public static class ParseDateUnSafety implements Runnable {

    int ss;

    ParseDateUnSafety(int i) {
      this.ss = i;
    }

    @Override
    public void run() {
      try {
        // sdf.parse 是线程不安全的 多线程共同使用会导致错误
        Date date = sdf.parse("2018-03-29 19:29:" + ss % 60);
        System.out.println(ss + ":" + date);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * 线程安全的
   */
  public static class ParseDateSafety implements Runnable {

    int ss;

    ParseDateSafety(int i) {
      this.ss = i;
    }

    @Override
    public void run() {
      try {
        // sdf.parse 是线程不安全的 多线程共同使用会导致错误
        // Date date = sdf.parse("2018-03-29 19:29:" + ss % 60);

        // 如果当前线程没有 SimpleDateFormat 对象新建一个到线程中去
        if (t1.get() == null) {
          t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }
        if (t2.get() == null) {
          t2.set(Thread.currentThread().getName());
        }
        Date date = t1.get().parse("2018-03-29 19:29:" + ss % 60);
        t2.get();
        //System.out.println(ss + ":" + date);
        //可以看到 各个线程访问的是同一个threadLocal 访问的是不同的 dateFormat (不同对象实例的hashcode不同 内存地址相关)
        System.out.printf("thread name:%s,ThreadLocal hashcode:%s ,dateFormat hashcode%s \r\n",
            Thread.currentThread().getName(), t1.hashCode(), t1.get().hashCode());
      } catch (ParseException e) {
        e.printStackTrace();
      } finally {
        // 在不需要threadLocal 对象时 调用remove方法讲threadLocal对象清除
        // threadLocal被定义为static , 由于 ThreadLocal 有强引用在，那么在 ThreadLocalMap 里对应的 Entry 的键会永远存在，那么执行 remove 的时候就可以正确进行定位到并且删除
        t1.remove();
      }
    }
  }

  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 1000; i++) {
      es.execute(new ParseDateSafety(i));
    }
  }
}
