package cn.leran.currentroad.chapter5.assemblyline;

import lombok.Data;

/**
 * 流水线消息,线程中信息交换.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
@Data
public class Msg {

  private double i;
  private double j;
  private String orgStr = null;
}
