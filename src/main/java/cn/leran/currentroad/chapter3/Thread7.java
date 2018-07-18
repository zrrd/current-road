package cn.leran.currentroad.chapter3;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义的线程池.
 *
 * @author shaoyijiong
 * @date 2018/7/17
 */
public class Thread7 {

  public static void main(String[] args) {

    ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("pool-%d").build();
    ExecutorService pool = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(1024),
        nameThreadFactory, new ThreadPoolExecutor.AbortPolicy());
  }
}
