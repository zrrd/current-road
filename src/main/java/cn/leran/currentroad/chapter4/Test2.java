package cn.leran.currentroad.chapter4;

/**
 * @author shaoyijiong
 * @date 2019/11/8
 */
public class Test2 {

  private static class Account {

    private int balance;

    /**
     * 同时锁定两个账户完成转账 但是会导致死锁
     */
    void transfer(Account target, int amt) {
      // 锁定转出账户
      synchronized (this) {
        // 锁定转入账户
        synchronized (target) {
          if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
          }
        }
      }
    }
  }
}
