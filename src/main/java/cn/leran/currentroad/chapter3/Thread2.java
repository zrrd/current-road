package cn.leran.currentroad.chapter3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * trylock 有限等待
 *
 * @author shaoyijiong
 * @date 2018/7/16
 */
public class Thread2 implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            //如果trylock不加时间的话 判断是否能获得锁 立即返回
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " get lock");
                Thread.sleep(6000);
            } else {
                System.out.println(Thread.currentThread().getName() + " get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread2 thread2 = new Thread2();
        Thread t1 = new Thread(thread2);
        Thread t2 = new Thread(thread2);
        t1.start();
        t2.start();
    }
}
