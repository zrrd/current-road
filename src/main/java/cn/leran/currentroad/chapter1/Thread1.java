package cn.leran.currentroad.chapter1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <pre>
 * 调用了start() 方法后线程没有马上执行 而是处于就绪状态(该线程已经获取了除了CPU资源以外的其他资源)
 * 等待获取CPU资源后才会处于真正的运行状态
 * </pre>
 * 启动一个线程
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread1 {


  /**
   * 1. 通过实现runnable接口开启线程
   */
  private void runnable() {
    //通过lambda表达式实现一个runnable接口
    Runnable runnable = () -> System.out.println("线程开始了");
    Thread thread = new Thread(runnable);
    thread.start();
  }


  /**
   * 2. 通过继承Thread实现一个线程
   */
  static class MyThread extends Thread {

    @Override
    public void run() {
      System.out.println("线程开始了");
    }
  }

  private void thread() {
    new MyThread().start();
  }


  /**
   * 3. 通过FutureTask 带有回调的线程
   */
  static class CallerTask implements Callable<String> {

    @Override
    public String call() throws Exception {
      return "hello";
    }
  }

  /**
   * <pre>
   * FutureTask 实现了两个接口 一个Runnable 一个Future
   * Runnable 用来开始线程
   * Future 用来控制线程状态
   *
   * FutureTask 定义了这些状态
   * private static final int NEW          = 0;
   * private static final int COMPLETING   = 1;
   * private static final int NORMAL       = 2;
   * private static final int EXCEPTIONAL  = 3;
   * private static final int CANCELLED    = 4;
   * private static final int INTERRUPTING = 5;
   * private static final int INTERRUPTED  = 6;
   *
   * 根据 FutureTask.run() 方法的执行的时机，FutureTask 分为了 3 种状态：
   * 未启动。FutureTask.run() 方法还没有被执行之前，FutureTask 处于未启动状态。当创建一个 FutureTask，还没有执行 FutureTask.run() 方法之前，FutureTask 处于未启动状态。
   * 已启动。FutureTask.run() 方法被执行的过程中，FutureTask 处于已启动状态。
   * 已完成。FutureTask.run() 方法执行结束，或者调用 FutureTask.cancel(...) 方法取消任务，或者在执行任务期间抛出异常，这些情况都称之为 FutureTask 的已完成状态。
   *
   * get() 方法当 FutureTask 处于未启动或已启动状态时，执行 FutureTask.get() 方法将导致调用线程阻塞。如果 FutureTask 处于已完成状态，
   * 调用 FutureTask.get() 方法将导致调用线程立即返回结果或者抛出异常
   *
   * cancel() 当 FutureTask 处于未启动状态时，执行 FutureTask.cancel() 方法将此任务永远不会执行；
   * 当 FutureTask 处于已启动状态时，执行 FutureTask.cancel(true) 方法将以中断线程的方式来阻止任务继续进行，
   * 如果执行 FutureTask.cancel(false) 将不会对正在执行任务的线程有任何影响；
   * 当 FutureTask 处于已完成状态时，执行 FutureTask.cancel(...) 方法将返回 false。
   *
   * isCancelled() 方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
   *
   * isDone() 方法表示任务是否已经完成，若任务完成，则返回true；
   * </pre>
   *
   */
  private static void futureTask() {
    // 创建异步任务
    FutureTask<String> futureTask = new FutureTask<>(new CallerTask());
    new Thread(futureTask).start();
    try {
      // 这里会阻塞当前线程
      String result = futureTask.get();
      System.out.println(result);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }


  /**
   * 一个简单的线程
   */
  public static void main(String[] args) {
    futureTask();
  }
}