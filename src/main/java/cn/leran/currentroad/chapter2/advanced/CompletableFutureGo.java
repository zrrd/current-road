package cn.leran.currentroad.chapter2.advanced;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author shaoyijiong
 * @date 2019/11/12
 */
@SuppressWarnings("all")
public class CompletableFutureGo {

  /**
   * <pre>
   * 主动完成计算通过阻塞或者轮询的方式获得结果
   * public T get()
   * public T get(long timeout, TimeUnit unit)
   * public T getNow(T valueIfAbsent)
   * public T join()
   * </pre>
   */
  private static void t1() {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      int i = 1 / 0;
      return 100;
    });
    // join 不需要手动捕获异常,如果发生异常会抛出对应的运行时异常
    // future.join();
    // get需要手动捕获异常
    try {
      future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  /**
   * <pre>
   * 取消任务 完成
   * complete
   * completeExceptionally
   * cancel
   * </pre>
   */
  private static void t2() {
    // 能够完成任务
    CompletableFuture<String> cf1 = CompletableFuture.completedFuture("1");
    // 10s后完成任务
    CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "1";
    });
    // 如果任务已经完成 返回任务完成后返回的值 否则 返回complete内的值
    System.out.println(cf1.complete("0"));     // false
    System.out.println(cf1.join()); // 1
    System.out.println(cf2.complete("0")); // true
    System.out.println(cf2.join()); // 0

    // 同理 未完成的话返回值替换成抛出异常
    cf1.completeExceptionally(new RuntimeException());
    cf1.cancel(true);
  }

  /**
   * <pre>
   * 创建CompletableFuture对象 如果没有指定线程池默认使用 ForkJoinPool.commonPool() 作为线程池
   * public static <U> CompletableFuture<U> completedFuture(U value)  返回一个已经计算好的CompletableFuture
   * public static CompletableFuture<Void> runAsync(Runnable runnable)  异步任务
   * public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
   * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
   * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
   * </pre>
   */
  private static void t3() {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      //长时间的计算任务
      return "·00";
    });
  }

  /**
   * <pre>
   * 计算结果完成时的处理 以Async结尾说明另起一个线程处理结果否则为当前线程处理结果
   * public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action)
   * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
   * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
   * public CompletableFuture<T> exceptionally(Function<Throwable,? extends T> fn)
   * </pre>
   */
  private static void t4() {
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
      int i = RandomUtils.nextInt(0, 10);
      if (i > 5) {
        return "0";
      } else {
        throw new IllegalArgumentException();
      }
    });
    CompletableFuture<String> cfComplete = cf.whenComplete((s, th) -> {
      System.out.println(s);
      System.out.println(th);
    });
    // 这两个是恒等的
    //System.out.println(cf.join());
    System.out.println(cfComplete.join());
  }

  /**
   * <pre>
   * 下面一组方法虽然也返回CompletableFuture对象，但是对象的值和原来的CompletableFuture计算的值不同。
   * 当原先的CompletableFuture的值计算完成或者抛出异常的时候，会触发这个CompletableFuture对象的计算，
   * 结果由BiFunction参数计算而得。因此这组方法兼有whenComplete和转换的两个功能。
   * public <U> CompletableFuture<U> handle(BiFunction<? super T,Throwable,? extends U> fn)
   * public <U> CompletableFuture<U> handleAsync(BiFunction<? super T,Throwable,? extends U> fn)
   * public <U> CompletableFuture<U> handleAsync(BiFunction<? super T,Throwable,? extends U> fn, Executor executor)
   * </pre>
   */
  private static void t5() {
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
      int i = RandomUtils.nextInt(0, 10);
      if (i > 5) {
        return "0";
      } else {
        throw new IllegalArgumentException();
      }
    });
    CompletableFuture<String> cfHandle = cf.handle((s, th) -> {
      System.out.println(s);
      System.out.println(th);
      return "1";
    });
    // 这两个值不恒等
    System.out.println(cf.join());
    System.out.println(cfHandle.join());
  }

  /**
   * <pre>
   * 转换
   * public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
   * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
   * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
   * </pre>
   */
  private static void t6() {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      return 100;
    });
    CompletableFuture<String> f = future.thenApplyAsync(i -> i * 10).thenApply(i -> i.toString());
    System.out.println(future.join()); //100
    System.out.println(f.join()); //"1000"
  }

  /**
   * <pre>
   * 纯消费(执行Action)
   * public CompletableFuture<Void> thenAccept(Consumer<? super T> action)
   * public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action)
   * public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action,  Executor executor)
   * </pre>
   */
  private static void t7() {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      return 100;
    });
    CompletableFuture<Void> f = future.thenAccept(System.out::println);
    System.out.println(f.join());
  }

  /**
   * <pre>
   * 当两步操作都完成后带Async时,action在新启动线程运行   runAfterBoth 都正常完成计算的时候,执行一个Runnable，这个Runnable并不使用计算的结果。
   * public <U> CompletableFuture<Void> 	thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T,? super U> action)
   * public <U> CompletableFuture<Void> 	thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T,? super U> action)
   * public <U> CompletableFuture<Void> 	thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T,? super U> action, Executor executor)
   * public     CompletableFuture<Void> 	runAfterBoth(CompletionStage<?> other,  Runnable action)
   * </pre>
   */
  private static void t8() {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      return 100;
    });
    CompletableFuture<Void> f = future
        .thenAcceptBoth(CompletableFuture.completedFuture(10), (x, y) -> System.out.println(x * y));
    System.out.println(f.join());
  }

  /**
   * <pre>
   * 更彻底地，下面一组方法当计算完成的时候会执行一个Runnable,与thenAccept不同，Runnable并不使用CompletableFuture计算的结果。
   * public CompletableFuture<Void> thenRun(Runnable action)
   * public CompletableFuture<Void> thenRunAsync(Runnable action)
   * public CompletableFuture<Void> thenRunAsync(Runnable action, Executor executor)
   * </pre>
   */
  private static void t9() {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      return 100;
    });
    CompletableFuture<Void> f = future.thenRun(() -> System.out.println("finished"));
    System.out.println(f.join());
  }

  /**
   * <pre>
   * 组合 A +--> B +---> C
   * public <U> CompletableFuture<U> thenCompose(Function<? super T,? extends CompletionStage<U>> fn)
   * public <U> CompletableFuture<U> thenComposeAsync(Function<? super T,? extends CompletionStage<U>> fn)
   * public <U> CompletableFuture<U> thenComposeAsync(Function<? super T,? extends CompletionStage<U>> fn, Executor executor)
   * </pre>
   */
  private static void t10() {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      return 100;
    });
    CompletableFuture<String> f = future.thenCompose(i -> {
      return CompletableFuture.supplyAsync(() -> {
        return (i * 10) + "";
      });
    });
    System.out.println(f.join()); //1000
  }

  /**
   * <pre>
   * thenCombine用来复合另外一个CompletionStage的结果
   * A +
   *   |
   *   +------> C
   *   +------^
   * B +
   * public <U,V> CompletableFuture<V> 	thenCombine(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)
   * public <U,V> CompletableFuture<V> 	thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)
   * public <U,V> CompletableFuture<V> 	thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn, Executor executor)
   * 两个CompletionStage是并行执行的，它们之间并没有先后依赖顺序，other并不会等待先前的CompletableFuture执行完毕后再执行。
   * </pre>
   */
  private static void t11() {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      return 100;
    });
    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
      return "abc";
    });
    CompletableFuture<String> f = future.thenCombine(future2, (x, y) -> y + "-" + x);
    System.out.println(f.join()); //abc-100
  }

  /**
   * <pre>
   * Either  thenAcceptBoth和runAfterBoth是当两个CompletableFuture都计算完成
   * acceptEither方法是当任意一个CompletionStage完成的时候，action这个消费者就会被执行。这个方法返回CompletableFuture<Void>
   * applyToEither方法是当任意一个CompletionStage完成的时候，fn会被执行，它的返回值会当作新的CompletableFuture<U>的计算结果。
   * public CompletableFuture<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)
   * public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action)
   * public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor)
   * public <U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> other, Function<? super T,U> fn)
   * public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T,U> fn)
   * public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T,U> fn, Executor executor)
   * </pre>
   */
  private static void t12() {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(RandomUtils.nextInt(0, 10));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 100;
    });
    CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(RandomUtils.nextInt(0, 10));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 200;
    });
    // 可能输出100 可能输出200
    CompletableFuture<String> f = future.applyToEither(future2, i -> i.toString());
    System.out.println(f.join());
  }

  /**
   * <pre>
   * 组合多个CompletableFuture
   * allOf方法是当所有的CompletableFuture都执行完后执行计算。
   * anyOf方法是当任意一个CompletableFuture执行完后就会执行计算，计算的结果相同。
   * public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
   * public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)
   * </pre>
   */
  private static void t13() {
    CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(RandomUtils.nextInt(0, 10));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 100;
    });
    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(RandomUtils.nextInt(0, 10));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "abc";
    });
    //CompletableFuture<Void> f =  CompletableFuture.allOf(future1,future2);
    CompletableFuture<Object> f =  CompletableFuture.anyOf(future1,future2);
    System.out.println(f.join());
  }

  public static void main(String[] args) {
    t13();
  }
}
