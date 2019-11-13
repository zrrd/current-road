package cn.leran.currentroad.chapter2.advanced;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author shaoyijiong
 * @date 2019/11/13
 */
public class ForkJoinGo {

  /**
   * 通过ForkJoin 计算斐波那契数列
   */

  //递归任务
  static class Fibonacci extends RecursiveTask<Integer> {

    final int n;

    Fibonacci(int n) {
      this.n = n;
    }

    protected Integer compute() {
      if (n <= 1) {
        return n;
      }
      Fibonacci f1 = new Fibonacci(n - 1);
      //创建子任务
      f1.fork();
      Fibonacci f2 = new Fibonacci(n - 2);
      //等待子任务结果，并合并结果
      return f2.compute() + f1.join();
    }
  }

  private static void t1() {
    //创建分治任务线程池
    ForkJoinPool fjp = new ForkJoinPool(4);
    //创建分治任务
    Fibonacci fib = new Fibonacci(30);
    //启动分治任务
    Integer result = fjp.invoke(fib);
    //输出结果
    System.out.println(result);
  }


  static class Max extends RecursiveTask<Integer> {

    final int[] array;

    Max(int[] array) {
      this.array = array;
    }

    @Override
    protected Integer compute() {
      if (array.length == 1) {
        return array[0];
      }
      // 分成两个任务
      Max f1 = new Max(Arrays.copyOfRange(array, 0, array.length / 2));
      f1.fork();
      Max f2 = new Max(Arrays.copyOfRange(array, array.length / 2, array.length));
      Integer n1 = f1.join();
      Integer n2 = f2.compute();
      return n1 > n2 ? n1 : n2;
    }
  }


  /**
   * 通过ForkJoin 实现在大量数据中找最大值
   */
  private static void t2() {
    int[] array = new int[1000];
    for (int i = 0; i < array.length; i++) {
      array[i] = RandomUtils.nextInt();
    }
    //创建分治任务线程池
    ForkJoinPool fjp = new ForkJoinPool(4);
    //创建分治任务
    Max fib = new Max(array);
    //启动分治任务
    Integer result = fjp.invoke(fib);
    //输出结果
    System.out.println(result);

  }

  public static void main(String[] args) {
    t2();
  }


}
