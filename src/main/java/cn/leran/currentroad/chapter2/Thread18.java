package cn.leran.currentroad.chapter2;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.Data;

/**
 * @author shaoyijiong
 * @date 2019/10/10
 */
public class Thread18 {

  private static class Task implements Comparable<Task> {

    String taskName;

    int priority;

    Task(String taskName, int priority) {
      this.taskName = taskName;
      this.priority = priority;
    }

    @Override
    public int compareTo(Task o) {
      if (this.priority >= o.priority) {
        return 1;
      } else {
        return -1;
      }
    }

    @Override
    public String toString() {
      return "taskNam:" + taskName + "---priority:" + priority;
    }
  }

  /**
   * 优先级队列测试
   */
  private static void test1() throws InterruptedException {
    Random random = new Random();
    PriorityBlockingQueue<Task> priorityBlockingQueue = new PriorityBlockingQueue<>();
    for (int i = 0; i < 10; i++) {
      priorityBlockingQueue.put(new Task("task" + i, random.nextInt(10)));
    }
    while (priorityBlockingQueue.peek() != null) {
      Task take = priorityBlockingQueue.take();
      System.out.println(take);
    }
  }


  @Data
  private static class DelayTask implements Delayed {


    Date startTime;
    String taskName;

    DelayTask(Date startTime, String taskName) {
      this.startTime = startTime;
      this.taskName = taskName;
    }

    @Override
    public long getDelay(TimeUnit unit) {
      long l = startTime.getTime() - new Date().getTime();
      return unit.toMillis(l);
    }

    @Override
    public int compareTo(Delayed o) {
      if (this.getDelay(TimeUnit.MILLISECONDS) >= o.getDelay(TimeUnit.MILLISECONDS)) {
        return 1;
      } else {
        return -1;
      }
    }

    @Override
    public String toString() {
      return "DelayTask{" + "startTime=" + startTime + ", taskName='" + taskName + '\'' + '}';
    }
  }

  private static void test2() throws InterruptedException {
    Random random = new Random();
    DelayQueue<DelayTask> delayQueue = new DelayQueue<>();
    for (int i = 0; i < 10; i++) {
      delayQueue.put(new DelayTask(new Date(System.currentTimeMillis() + random.nextInt(10) * 1000),"task" + i));
    }
    while (delayQueue.peek() != null) {
      DelayTask take = delayQueue.take();
      System.out.println(take);
    }
  }





  public static void main(String[] args) throws InterruptedException {
    test2();
  }
}
