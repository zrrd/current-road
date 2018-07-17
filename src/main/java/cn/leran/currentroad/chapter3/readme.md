1.  Thread1   ReentrantLock 重入锁 可以加锁多次
2.  Thread2   tryLock       尝试获得锁资源
3.  Thread3   condition     wait 和 signal线程同步
4.  Thread4   ReentrantReadWriteLock 读写锁 处理写少读多的情况效率更高