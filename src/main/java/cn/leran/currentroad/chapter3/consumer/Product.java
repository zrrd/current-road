package cn.leran.currentroad.chapter3.consumer;

import lombok.Getter;

/**
 * 不可变类. final 类 不可继承 final 变量 不可修改
 *
 * @author shaoyijiong
 * @date 2018/7/19
 */
@Getter
public final class Product {

  private final String no;

  private final String name;

  private final double price;

  public Product(String no, String name, double price) {
    this.no = no;
    this.name = name;
    this.price = price;
  }
}
