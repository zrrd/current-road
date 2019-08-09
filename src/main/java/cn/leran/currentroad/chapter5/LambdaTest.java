package cn.leran.currentroad.chapter5;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import org.springframework.util.StopWatch;

/**
 * lambda Test.
 *
 * @author shaoyijiong
 * @date 2018/7/23
 */
public class LambdaTest {

  public static class PrimeUntil {

    public boolean isPrime(int num) {
      int tmp = num;
      if (tmp < 2) {
        return false;
      }
      for (int i = 2; Math.sqrt(tmp) >= i; i++) {
        if (tmp % i == 0) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * lambda 中的并行操作
   */
  public static void main(String[] args) {
    int[] num = {1, 2, 3, 4, 5, 6, 7};
    Arrays.stream(num).forEach(System.out::println);
    Random random = new Random();
    //并行排序
    Arrays.parallelSort(num);
    //并行赋值
    Arrays.parallelSetAll(num, (i) -> random.nextInt());

    StopWatch sw = new StopWatch();
    sw.start();
    long count1 = IntStream.range(1, 100000).filter(new PrimeUntil()::isPrime).count();
    sw.stop();
    System.out.println(count1 + "串行的时间" + sw.getLastTaskTimeMillis());
    sw.start();

    //加上parallel 将这个串行的改成并行的
    long count2 = IntStream.range(1, 100000).parallel().filter(new PrimeUntil()::isPrime).count();
    sw.stop();
    System.out.println(count2 + "并行的时间" + sw.getLastTaskTimeMillis());
  }
}
