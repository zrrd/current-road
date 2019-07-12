package cn.leran.currentroad.chapter4.disruptor;

import com.lmax.disruptor.RingBuffer;
import java.nio.ByteBuffer;

/**
 * 生产者.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
class Producer {

  /**
   * 环形缓冲区.
   */
  private final RingBuffer<PcData> ringBuffer;

  Producer(RingBuffer<PcData> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }

  /**
   * 讲数据推入缓冲区,ByteBuffer可以用来包装任何数据类型,将ByteBuffer数据提取出来,装载到环形缓冲区.
   *
   * @param bb 通过ByteBuffer讲数据放入数据缓冲区
   */
  void pushDate(ByteBuffer bb) {
    //通过next得到下一个可用序列号,通过序列号得到下一个空闲可用的PcDate
    //讲这个PcDate数据设置为期望值
    long sequence = ringBuffer.next();
    try {
      //将数据放入
      PcData event = ringBuffer.get(sequence);
      event.setValue(bb.getLong(0));
    } finally {
      //将产品输出
      ringBuffer.publish(sequence);
    }

  }
}
