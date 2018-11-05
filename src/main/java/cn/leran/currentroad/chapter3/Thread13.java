package cn.leran.currentroad.chapter3;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Callable.
 *
 * @author 邵益炯
 * @date 2018/11/5
 */
public class Thread13 {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    Callable<Integer> task = () -> {
      TimeUnit.SECONDS.sleep(1);
      return 1;
    };

    //通过future来获得上面task的返回结果
    ExecutorService executor = Executors.newFixedThreadPool(1);
    Future<Integer> future = executor.submit(task);

    //判断是否走完
    System.out.println("future done? " + future.isDone());

    //阻塞当前进程 直到返回结果
    Integer result = future.get();
    //最长等待1s 否则抛出一个TimeoutException
    Integer result2 = future.get(1, TimeUnit.SECONDS);

    System.out.println("future done? " + future.isDone());
    System.out.print("result: " + result);


    //批量提交请求
    ExecutorService executor1 = Executors.newWorkStealingPool();

    List<Callable<String>> callables = Arrays.asList(
        () -> "task1",
        () -> "task2",
        () -> "task3");

    //获得所有callable线程的请求结果集
    List<Future<String>> futures = executor1.invokeAll(callables);

  }
}
