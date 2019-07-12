package cn.leran.currentroad.chapter4.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;

/**
 * 并行查找.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
@Data
public class Search {

  private static int[] arr;
  private static ExecutorService pool = Executors.newCachedThreadPool();
  private static final int THREAD_NUM = 2;
  private static AtomicInteger result = new AtomicInteger();

  private static int search(int searchValue, int beginPos, int endPos) {
    int i;
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

    SearchTask(int begin, int end, int searchValue) {
      this.begin = begin;
      this.end = end;
      this.searchValue = searchValue;
    }

    @Override
    public Integer call() {
      return search(searchValue, begin, end);
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
