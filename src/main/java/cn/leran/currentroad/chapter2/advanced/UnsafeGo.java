package cn.leran.currentroad.chapter2.advanced;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

/**
 * @author shaoyijiong
 * @date 2019/8/22
 */
public class UnsafeGo {

  private volatile int value = 0;

  /**
   * 提供硬件级别的原子操作  Unsafe.getUnsafe() 方法有判断加载器是否为BootStrap加载器 但是本类使用的是 AppClassLoader
   */
  private static Unsafe unsafe;

  private static long offset;

  static {
    try {
      // 使用反射获取 Unsafe 的成员变量 theUnsafe
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      // 设置为可以存取
      field.setAccessible(true);
      unsafe = (Unsafe) field.get(null);
      offset = unsafe.objectFieldOffset(UnsafeGo.class.getDeclaredField("value"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 测试
   */
  public static void main(String[] args) throws NoSuchFieldException {

    UnsafeGo unsafeGo = new UnsafeGo();
    Unsafe unsafe = unsafeGo.unsafe;

    // 内存相关 提供的3个本地方法allocateMemory、reallocateMemory、freeMemory分别用于分配内存，扩充内存和释放内存，与C语言中的3个方法对应。
    unsafe.allocateMemory(0);
    unsafe.reallocateMemory(0, 0);
    unsafe.freeMemory(0);

    // 地址相关
    // 获取指定变量在所属类中内存偏移地址 内存地址
    long offsetValue = unsafe.objectFieldOffset(UnsafeGo.class.getDeclaredField("value"));
    // 获取对象中offset偏移地址对应的整型field的值,支持volatile  能与上面方法配合使用
    int intVolatile = unsafe.getIntVolatile(unsafeGo, offsetValue);
    // 获取对象中offset偏移地址对应的long型field的值
    unsafe.getLong(offsetValue);

    //可以获取数组第一个元素的偏移地址
    unsafe.arrayBaseOffset(List.class);
    //可以获取数组的转换因子，也就是数组中元素的增量地址
    unsafe.arrayIndexScale(List.class);

    // 挂起与恢复
    // void park(boolean isAbsolute,long time) 阻塞当前线程 , 其中参数 isAbsolute 等于 false 且 time 等于 0 表示一直阻塞
    // time 大于 0 表示等待指定的 time 后阻塞线程会被唤醒 , time 是相对值 比如 1000 表示当前时间增加1s
    // 如果 isAbsolute 等于 true , 并且 time 大于 0 , 表示阻塞到指定时间点后会被唤醒 , 这里的time 是绝对时间
    // 其他线程调用当前阻塞线程的 interrupt 方法而中断了当前当前线程时当前线程会返回
    // 其他线程调用了 unpark() 方法 , 并且以当前线程为参数时 , 当前线程也会返回
    unsafe.park(true, 0);
    unsafe.unpark(Thread.currentThread());

    // CAS 操作
    // unsafe.compareAndSwapLong()
    // unsafe.compareAndSwapObject()
    // 对象 对象值的内存地址  预期原址 新址
    unsafe.compareAndSwapInt(unsafeGo, offsetValue, 1, 2);
  }


  @Test
  public void go() {
    UnsafeGo unsafeGo = new UnsafeGo();
    int intVolatile = unsafe.getIntVolatile(unsafeGo, offset);
    System.out.println("init:" + intVolatile);
    boolean success = unsafe.compareAndSwapInt(unsafeGo, offset, 0, 10);
    System.out.println(success + " " + unsafeGo.value);
  }

}
