package cn.leran.currentroad.chapter5.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 运行下.
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class GoGoGo {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    PCDataFactory factory = new PCDataFactory();
    //设置缓冲区大小 2的整数次幂
    int bufferSize = 1024;
    Disruptor<PcData> disruptor = new Disruptor<>(factory, bufferSize, executorService,
        ProducerType.MULTI, new BlockingWaitStrategy());
    disruptor
        .handleEventsWithWorkerPool(new Consumer(), new Consumer(), new Consumer(), new Consumer(),
            new Consumer());
    disruptor.start();
    RingBuffer<PcData> ringBuffer = disruptor.getRingBuffer();
    Producer producer = new Producer(ringBuffer);
    ByteBuffer bb = ByteBuffer.allocate(8);
    for (long l = 0; true; l++) {
      bb.putLong(0, l);
      producer.pushDate(bb);
      Thread.sleep(100);
      System.out.println("add data " + l);
    }
  }
}
