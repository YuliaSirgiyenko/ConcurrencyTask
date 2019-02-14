import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ReadThread implements Runnable {

    int sum = 0;
    String threadName;
    CountDownLatch countDownLatch;
    BlockingQueue<Integer> queue;

    ReadThread(String name, CountDownLatch countDownLatch, BlockingQueue<Integer> queue) {
        this.threadName = name;
        System.out.println(threadName + " ready for start");
        this.countDownLatch = countDownLatch;
        countDownLatch.countDown();
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            while (ThreadManager.flag) {
                int readItem = queue.poll(2000, TimeUnit.MILLISECONDS);
                //System.out.println(threadName + " read " + readItem);
                sum += readItem;
                System.out.println("Sum of " + threadName + " counts " + sum);

                if (sum >= 100 && ThreadManager.flag) {
                    ThreadManager.flag = false;
                    System.out.println("Reading thread " + threadName + " is winner");
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException | NullPointerException e) {
            System.out.println("Interrupted: " + threadName);
        }
    }

}
