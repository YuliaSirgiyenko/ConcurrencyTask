import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class BlockingQueueImpl {

    static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10, true);
    static ArrayList<Thread> threadsPool = new ArrayList(7);
    static final CountDownLatch COUNT_DOWN_LATCH_START = new CountDownLatch(8);

    void init(){
        try {
            for (int i = 1; i < 6; i++) {
                Thread t = new Thread(new GenerateThread(), "Writer " + i);
                t.start();
                threadsPool.add(t);
            }
            for (int j = 1; j < 3; j++) {
                Thread t = new Thread(new ReadThread(), "Reader " + j);
                t.start();
                threadsPool.add(t);
            }

            while (COUNT_DOWN_LATCH_START.getCount() != 1) {
                Thread.sleep(1000);
            }
            System.out.println("All threads ready");
            COUNT_DOWN_LATCH_START.countDown();
        } catch (InterruptedException e) {
            System.out.println("BlockingQueue is interrupted.");
        }
    }

    static void threadPreparation() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " ready");
        BlockingQueueImpl.COUNT_DOWN_LATCH_START.countDown();
        BlockingQueueImpl.COUNT_DOWN_LATCH_START.await();
    }

}
