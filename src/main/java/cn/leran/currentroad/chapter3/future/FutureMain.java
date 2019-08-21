package cn.leran.currentroad.chapter3.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 使用Future.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class FutureMain {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    //构造函数 要一个实现callable的类 注意这里的泛型是返回类型  构造函数式需要一个实现Callable的类
    FutureTask<String> futureTask = new FutureTask<>(new RealData("a"));
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(futureTask);
    System.out.println("请求完毕");
    //模拟请求干别的事
    Thread.sleep(2000);
    System.out.println("主线程干完了");
    //获得执行完的futureTask 的结果,如果没有结果的话,主线程会阻塞.
    System.out.println("数据为=" + futureTask.get());
  }
}
