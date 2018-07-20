package cn.leran.currentroad.chapter5.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并行查找.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class Search {

  static int[] arr;
  static ExecutorService pool = Executors.newCachedThreadPool();
  static final int THREAD_NUM = 2;
  static AtomicInteger result = new AtomicInteger();

  public static int search(int searchValue, int beginPos, int endPos) {
    int i = 0;
    for (i = beginPos; i < endPos; i++) {
      //其他线程已经找到了
      if (result.get() >= 0) {
        return result.get();
      }
      if (arr[i] == searchValue) {
        if (!result.compareAndSet(-1, i)) {
          return result.get();
        }
      }
    }
    return -1;
  }

  public static class SearchTask implements Callable<Integer> {

    int begin;
    int end;
    int searchValue;

    public SearchTask(int begin, int end, int searchValue) {
      this.begin = begin;
      this.end = end;
      this.searchValue = searchValue;
    }

    @Override
    public Integer call() throws Exception {
      int re = search(searchValue, begin, end);
      return re;
    }
  }

  public static int pSearch(int searchValue) throws ExecutionException, InterruptedException {
    int subArrSize = arr.length / THREAD_NUM + 1;
    List<Future<Integer>> re = new ArrayList<>();
    for (int i = 0; i < arr.length; i += subArrSize) {
      int end = i + subArrSize;
      if (end > arr.length) {
        end = arr.length;
      }
      re.add(pool.submit(new SearchTask(searchValue, i, end)));
    }
    for (Future<Integer> future : re) {
      if (future.get() > 0) {
        return future.get();
      }
    }
    return -1;
  }

}
