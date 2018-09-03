1.  Thread1   ReentrantLock 重入锁 可以加锁多次
2.  Thread2   tryLock       尝试获得锁资源
3.  Thread3   condition     wait 和 signal线程同步
4.  Thread4   ReentrantReadWriteLock 读写锁 处理写少读多的情况效率更高
5.  Thread5   CountDownLatch 计数器 所有线程完成任务
6.  Thread6   lockSupport 线程阻塞工具 不必担心线程死锁
7.  Thread7   ThreadPoolExecutor 自定义的线程池
8.  Thread8   Fork/Join框架 任务拆分执行
9.  Thread9   线程安全的Java数据结构
10. Thread10  StampedLock优化的读写锁
11. Thread11  通过CountDownLatch进行多线程的协同
12. Thread12  通过CyclicBarrier进行多线程协同(可以多次使用)