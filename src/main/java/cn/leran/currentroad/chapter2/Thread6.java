package cn.leran.currentroad.chapter2;

/**
 * 线程组
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread6 {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("group");
        Runnable runnable = ()-> System.out.println(Thread.currentThread().getName());
        Thread t1 = new Thread(threadGroup,runnable,"T1");
        Thread t2 = new Thread(threadGroup,runnable,"T2");
        t1.start();
        t2.start();
        System.out.println(threadGroup.activeCount());
        threadGroup.list();
    }
}
