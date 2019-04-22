import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/*Parent class for GenerateThread and ReadThread.*/
public class AppThread implements Runnable {

    String threadName;
    CountDownLatch countDownLatch;
    BlockingQueue<Integer> queue;

    AppThread(String name, CountDownLatch countDownLatch, BlockingQueue<Integer> queue) {
        this.threadName = name;
        this.countDownLatch = countDownLatch;
        this.queue = queue;
    }

    @Override
    public void run() {

    }
}
