package cn.leran.currentroad.chapter3.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 在disruptor系统初始化时,构造所有缓存区中的对象实例.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class PcDataFactory implements EventFactory<PcData> {

  @Override
  public PcData newInstance() {
    return new PcData();
  }
}
