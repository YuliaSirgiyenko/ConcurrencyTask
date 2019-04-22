import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*Describe thread which read numbers from general queue.
* After sum of read numbers will comprise 100 of higher,
* all other threads and application in a whole will be stopped.*/
public class ReadThread extends AppThread {

    int sum = 0;

    ReadThread(String name, CountDownLatch countDownLatch, BlockingQueue<Integer> queue) {
        super(name, countDownLatch, queue);
    }

    /*Started thread sends information about start by decrease of number of
    threads in waiting mode by 1. Current thread goes in waiting mode till
    all threads readiness for work.
    After removal of waiting mode, the main logic of method is started -
    thread read numbers from general queue. After sum of read numbers will
    comprise 100 of higher, all other threads and application in a whole will be stopped.*/
    @Override
    public void run() {
        try {
            countDownLatch.countDown();
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
