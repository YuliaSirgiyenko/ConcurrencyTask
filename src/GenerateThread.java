import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GenerateThread implements Runnable {

    String threadName;
    CountDownLatch countDownLatch;
    BlockingQueue<Integer> queue;

    GenerateThread(String name, CountDownLatch countDownLatch, BlockingQueue<Integer> queue) {
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
            while(ThreadManager.flag) {
                int writeItem = (int) ((Math.random() * 5) + 1);
                queue.offer(writeItem, 2000, TimeUnit.MILLISECONDS);
                //System.out.println(threadName + " write " + writeItem);
                Thread.sleep(500);
            }
        } catch (InterruptedException | NullPointerException e) {
            System.out.println("Interrupted: " + threadName);
        }
    }

}
