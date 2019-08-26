package cn.leran.currentroad.chapter2.advanced;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author shaoyijiong
 * @date 2019/8/23
 */
public class ThreadLocalRandomGo {


  /**
   * <pre>
   * Random 的随机数生成方式
   * 初始化一个种子 , 通过种子生成随机数
   * 生成下一个随机数时 , 需要通过老的种子生成一个新的种子 , 再用新的种子生成随机数
   * random 中的种子 是由一个AtomicLong来维护的 , 保证只有一个线程生成一个新的种子 , 当并发量高时 , 种子的生成
   * 其他线程的 cas 失败 , 消耗大量性能
   * </pre>
   */
  private Random random = new Random();


  public static void main(String[] args) {



    // 存放在Thread 中的种子生成器
    //  long threadLocalRandomSeed;
    //  当前线程随机数
    //  int threadLocalRandomProbe;
    //  int threadLocalRandomSecondarySeed;
    Thread thread = Thread.currentThread();

    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
  }
}
