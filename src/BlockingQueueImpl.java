import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class BlockingQueueImpl {

    static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10, true);
    static boolean flag = true;
    static final CountDownLatch COUNT_DOWN_LATCH_START = new CountDownLatch(8);

    void init() {
        try {
            for (int i = 1; i < 6; i++) {
                (new Thread(new GenerateThread(), "Writer " + i)).start();
            }
            for (int j = 1; j < 3; j++) {
                (new Thread(new ReadThread(), "Reader " + j)).start();
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
