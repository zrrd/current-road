package cn.leran.currentroad.chapter3.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 运行下. 从功能上来说，Disruptor 是实现了"队列"的功能，而且是一个有界队列，也可以被看作是一个轻量级的消息中间件，所以它的应用场景自然就是典型的"生产者-消费者"模型的应用场合了。
 *
 * @author shaoyijiong
 * @date 2018/7/20
 */
public class GoGoGo {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    PcDataFactory factory = new PcDataFactory();
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
