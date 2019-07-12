package cn.leran.currentroad.chapter2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join框架.
 *
 * @author shaoyijiong
 * @date 2018/7/17
 */
public class Thread8 extends RecursiveTask<Long> {

  /**
   * 分解任务的规模.
   */
  private static final int THRESHOLD = 10000;
  private long start;
  private long end;

  public Thread8(long start, long end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    long sum = 0;
    boolean canCompute = (end - start) < THRESHOLD;
    if (canCompute) {
      for (long i = start; i <= end; i++) {
        sum += i;
      }
    } else {
      //分成100个小任务
      long step = (start + end) / 100;
      ArrayList<Thread8> subTasks = new ArrayList<>();
      long pos = start;
      for (int i = 0; i < 100; i++) {
        long lastOne = pos + step;
        if (lastOne > end) {
          lastOne = end;
        }
        //分解任务 分成100个task执行  跟递归的思想很像
        Thread8 subTask = new Thread8(pos, lastOne);
        pos += step + 1;
        subTasks.add(subTask);
        subTask.fork();
      }
      for (Thread8 task : subTasks) {
        sum += task.join();
      }
    }
    return sum;

  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    //计算0到200000的任务
    Thread8 task = new Thread8(0, 200000L);
    //将任务交到线程池
    ForkJoinTask<Long> result = forkJoinPool.submit(task);
    long res = result.get();
    System.out.println("sum =" + res);
  }
}
