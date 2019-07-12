package cn.leran.currentroad.chapter1;

public enum Status {
    //刚刚创建的进程，未执行
    NEW,
    //线程执行
    RUNNABLE,
    //遇到同步块，阻塞，直到获得请求的锁
    BLOCKED,
    //无限等待
    //通过wait()方法进入等待后的线程等待notify()
    //通过join()方法进入等待后的线程等待目标线程的终止
    WAITING,
    //有限等待
    TIMED_WAITING,
    //结束
    TERMINATED
}
